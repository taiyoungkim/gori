package com.team_gori.gori.feature_chat.presentation

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team_gori.gori.feature_chat.domain.model.ChatConnectionState
import com.team_gori.gori.feature_chat.domain.usecase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ChatViewModel(
    observeChatConnectionUseCase: ObserveChatConnectionUseCase,
    observeMessagesUseCase: ObserveMessagesUseCase,
    sendMessageUseCase: SendMessageUseCase,
    connectChatUseCase: ConnectChatUseCase,
    disconnectChatUseCase: DisconnectChatUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ChatState())
    val uiState = _uiState.asStateFlow()
    private val _sideEffect = MutableSharedFlow<ChatSideEffect>()
    val sideEffect: SharedFlow<ChatSideEffect> = _sideEffect.asSharedFlow()

    init {
//        observeChatConnectionUseCase().onEach { cs -> _state.update { it.copy(connectionState = cs) } }.launchIn(viewModelScope)
//        observeMessagesUseCase().onEach { msg -> _state.update { it.copy(messages = it.messages + msg) } }.launchIn(viewModelScope)
        loadMockChatMessages()
    }

    private fun loadMockChatMessages() {
        viewModelScope.launch {
            _uiState.update { it.copy(connectionState = ChatConnectionState.Connected, isLoading = false) }

            // 1. 원본 메시지 목록을 가져옵니다.
            val rawMessages = createMockMessages()
            // 2. 날짜 구분선을 포함한 최종 목록으로 가공합니다.
            val processedList = processMessagesWithDateSeparators(rawMessages)

            _uiState.update {
                it.copy(items = processedList)
            }
        }
    }

    /**
     * 메시지 목록을 순회하며 날짜가 바뀔 때마다 DateSeparator를 삽입합니다.
     */
    private fun processMessagesWithDateSeparators(messages: List<ChatMessage>): List<ChatListItem> {
        val listWithSeparators = mutableListOf<ChatListItem>()
        var lastDate: String? = null

        messages.forEach { message ->
            if (message.sentDate != lastDate) {
                // 날짜가 변경되면 DateSeparator 추가
                // TODO: 실제 앱에서는 "2025-07-06" 같은 문자열을 파싱하여 요일을 계산해야 합니다.
                listWithSeparators.add(DateSeparator(dateText = "${message.sentDate} (일)"))
                lastDate = message.sentDate
            }
            listWithSeparators.add(message)
        }
        return listWithSeparators
    }

    fun processIntent(intent: ChatIntent) {
        when (intent) {
            is ChatIntent.MessageInputChange -> {
                _uiState.update { it.copy(currentMessageInput = intent.text) }
            }
            is ChatIntent.SendMessage -> {
                val messageText = uiState.value.currentMessageInput

                if (messageText.isNotBlank()) {
                    println("SendMessage Intent: Text='${messageText}'개")
                    _uiState.update {
                        it.copy(currentMessageInput = "")
                    }
                }
            }
            is ChatIntent.SendImages -> {
                viewModelScope.launch {
                    // 선택된 이미지들로 이미지 메시지 생성
                    val newImageMessages = intent.imageBytes.map { bytes ->
                        ChatMessage(
                            id = bytes.hashCode().toLong(),
                            senderId = uiState.value.currentUserId,
                            content = null,
                            imageByteArray = bytes,
                            timestamp = "",
                            sentDate = "2025. 07. 07",
                            isFirstInSequence = true
                        )
                    }
                    // 기존 메시지 리스트에 새 이미지 메시지 추가
                    _uiState.update {
                        it.copy(items = it.items + newImageMessages)
                    }
                }
            }
            is ChatIntent.LongPressMessage -> {
                _uiState.update { it.copy(longPressedMessageId = intent.messageId) }
            }
            is ChatIntent.ClearFocusMode -> {
                _uiState.update {
                    it.copy(longPressedMessageId = null)
                }
            }
            else -> {

            }
        }
    }

    private fun createMockMessages(): List<ChatMessage> {
        val myUserId = _uiState.value.currentUserId
        return listOf(
            ChatMessage(id = 1, senderId = "partner_123", content = "안녕하세요!", timestamp = "오후 2:30", sentDate = "2025. 07. 05", isFirstInSequence = true),
            ChatMessage(id = 2, senderId = "partner_123", content = "혹시 지금 참여 가능한가요?", timestamp = "오후 2:30", sentDate = "2025. 07. 05", isFirstInSequence = false),
            ChatMessage(id = 3, senderId = myUserId, content = "네, 안녕하세요!", timestamp = "오후 2:31", sentDate = "2025. 07. 05", isFirstInSequence = true),
            ChatMessage(id = 4, senderId = "partner_123", content = "다음 날 메시지입니다.", timestamp = "오전 9:10", sentDate = "2025. 07. 06", isFirstInSequence = true),
            ChatMessage(id = 5, senderId = myUserId, content = "확인했습니다. 감사합니다.", timestamp = "오전 9:12", sentDate = "2025. 07. 06", isFirstInSequence = true),
            ChatMessage(id = 6, senderId = myUserId, content = "오늘도 좋은 하루 보내세요!", timestamp = "오전 9:12", sentDate = "2025. 07. 06", isFirstInSequence = false)
        )
    }
}

data class ChatState(
    val items: List<ChatListItem> = emptyList(),
    val connectionState: ChatConnectionState = ChatConnectionState.Disconnected,
    val currentMessageInput: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val currentUserId: String = "user_me",
    val longPressedMessageId: Long? = null,
)