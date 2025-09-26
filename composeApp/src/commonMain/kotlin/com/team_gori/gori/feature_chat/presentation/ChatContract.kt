package com.team_gori.gori.feature_chat.presentation

/**
 * 채팅방 LazyColumn에 표시될 아이템의 공통 인터페이스.
 * 메시지와 날짜 구분선을 모두 표현합니다.
 */
sealed interface ChatListItem {
    val key: String
}

data class ChatMessage(
    val id: Long,
    val senderId: String,
    val content: String? = null,
    val imageByteArray: ByteArray? = null,
    val timestamp: String,
    val sentDate: String,
    val isFirstInSequence: Boolean
) : ChatListItem {
    override val key: String get() = "msg-$id"

    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as ChatMessage

        if (id != other.id) return false
        if (senderId != other.senderId) return false
        if (content != other.content) return false
        if (imageByteArray != null) {
            if (other.imageByteArray == null) return false
            if (!imageByteArray.contentEquals(other.imageByteArray)) return false
        } else if (other.imageByteArray != null) return false
        if (timestamp != other.timestamp) return false
        if (sentDate != other.sentDate) return false
        if (isFirstInSequence != other.isFirstInSequence) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + senderId.hashCode()
        result = 31 * result + (content?.hashCode() ?: 0)
        result = 31 * result + (imageByteArray?.contentHashCode() ?: 0)
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + sentDate.hashCode()
        result = 31 * result + isFirstInSequence.hashCode()
        return result
    }
}

/**
 * 날짜 구분선을 위한 데이터 클래스. ChatListItem을 구현합니다.
 */
data class DateSeparator(
    val dateText: String // 화면 표시용 날짜 (예: "2025. 07. 06 (일)")
) : ChatListItem {
    override val key: String get() = "date-$dateText"
}

sealed interface ChatIntent {
    data class MessageInputChange(val text: String) : ChatIntent
    object SendMessage : ChatIntent
    data class SendImages(val imageBytes: List<ByteArray>) : ChatIntent
    object Connect : ChatIntent
    object Disconnect : ChatIntent
    object LoadPreviousMessages : ChatIntent
    data class LongPressMessage(val messageId: Long?) : ChatIntent
    data object ClearFocusMode : ChatIntent
}

sealed interface ChatSideEffect { data class ShowSnackbar(val message: String) : ChatSideEffect }