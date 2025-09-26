package com.team_gori.gori.feature_chat.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.Clipboard
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.preat.peekaboo.image.picker.toImageBitmap
import com.team_gori.gori.designsystem.component.GoriActionSheet
import com.team_gori.gori.designsystem.component.GoriChatRoomDateSeparatorChip
import com.team_gori.gori.designsystem.component.GoriChatTextField
import com.team_gori.gori.designsystem.component.GoriImageChatBubble
import com.team_gori.gori.designsystem.component.GoriMyChatBubble
import com.team_gori.gori.designsystem.component.GoriNoticeText
import com.team_gori.gori.designsystem.component.GoriYourChatBubble
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_arrow_left
import gori.composeapp.generated.resources.ic_menu
import kotlinx.coroutines.flow.collectLatest
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatRoomScreen(
    onNavigateBack: () -> Unit,
    onNavigateToChatSetting: (Int) -> Unit,
    viewModel: ChatViewModel = koinInject()
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }
    val isFocusMode = uiState.longPressedMessageId != null

    // 선택된 아이템의 레이아웃 정보(좌표, 크기)와 Composable을 저장할 상태
    var selectedItemLayout by remember { mutableStateOf<Rect?>(null) }
    var selectedItemComposable by remember { mutableStateOf<(@Composable () -> Unit)?>(null) }
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    var showActionSheet by remember { mutableStateOf(false) }
    var messageToCopy by remember { mutableStateOf<String?>(null) }

    val clipboardManager = LocalClipboardManager.current


    LaunchedEffect(isFocusMode) {
        if (!isFocusMode) {
            selectedItemLayout = null
            selectedItemComposable = null
            showActionSheet = false // 포커스 모드 해제 시 액션 시트도 함께 숨김
            messageToCopy = null
        }
    }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collectLatest { effect ->
            when (effect) {
                is ChatSideEffect.ShowSnackbar -> snackbarHostState.showSnackbar(effect.message)
            }
        }
    }

    LaunchedEffect(uiState.items) {
        if (uiState.items.isNotEmpty()) {
            listState.animateScrollToItem(uiState.items.lastIndex)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. 기본 UI (Scaffold와 그 내용물)를 먼저 그립니다.
        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "채팅방 (${uiState.connectionState})",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Image(
                                painterResource(Res.drawable.ic_arrow_left),
                                contentDescription = "닫기",
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { onNavigateToChatSetting(5) }) { // 임시 멤버 수
                            Image(
                                painterResource(Res.drawable.ic_menu),
                                contentDescription = "세팅",
                            )
                        }
                    },
                )
            },
            bottomBar = {
                GoriChatTextField(
                    text = uiState.currentMessageInput,
                    onTextChanged = { viewModel.processIntent(ChatIntent.MessageInputChange(it)) },
                    onSendMessage = { viewModel.processIntent(ChatIntent.SendMessage) },
                    scope = rememberCoroutineScope(),
                    setImageBitmapLists = { _, byteArrays ->
                        viewModel.processIntent(ChatIntent.SendImages(byteArrays))
                    }
                )
            }
        ) { innerPadding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .imePadding()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                stickyHeader {
                    GoriNoticeText(
                        text = "다음 파크 골프 일정 공유합니다. 4월 25일\n" +
                                "금요일 오후 8시에 신논현역에서 버스 대절하여"
                    )
                }
                items(
                    items = uiState.items,
                    key = { item -> item.key }
                ) { item ->
                    val isSelected = uiState.longPressedMessageId == (item as? ChatMessage)?.id

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { coordinates ->
                                // 이 아이템이 선택되었다면, 화면 내 절대 좌표를 상태에 저장
                                if (isSelected) {
                                    selectedItemLayout = coordinates.boundsInWindow()
                                }
                            }
                    ) {
                        // 실제 컨텐츠를 Composable 람다로 정의
                        val chatContent = @Composable {
                            val alignment = if ((item as? ChatMessage)?.senderId == uiState.currentUserId) Alignment.CenterEnd else Alignment.CenterStart
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = alignment
                            ) {
                                when (item) {
                                    is ChatMessage -> {
                                        if (item.imageByteArray != null) {
                                            val image = item.imageByteArray.toImageBitmap()
                                            GoriImageChatBubble(imageBitmap = image, time = item.timestamp, isFirst = item.isFirstInSequence)
                                        } else if (item.content != null) {
                                            if (item.senderId == uiState.currentUserId) {
                                                GoriMyChatBubble(text = item.content, time = item.timestamp, isFirst = item.isFirstInSequence)
                                            } else {
                                                GoriYourChatBubble(text = item.content, time = item.timestamp, isFirst = item.isFirstInSequence)
                                            }
                                        }
                                    }
                                    is DateSeparator -> {
                                        GoriChatRoomDateSeparatorChip(
                                            text = item.dateText,
                                            modifier = Modifier.padding(vertical = 8.dp),
                                        )
                                    }
                                }
                            }
                        }

                        // 이 아이템이 선택되었다면, Composable 람다 자체를 상태에 저장
                        if (isSelected) {
                            selectedItemComposable = chatContent
                        }

                        // LongPress 입력을 감지하기 위한 Box
                        Box(
                            modifier = Modifier.pointerInput(item.key) {
                                detectTapGestures(
                                    onLongPress = {
                                        if (item is ChatMessage) {
                                            viewModel.processIntent(ChatIntent.LongPressMessage(item.id))
                                            if (item.content != null) {
                                                messageToCopy = item.content
                                            }
                                            showActionSheet = true // 액션 시트 보여주기
                                        }
                                    }
                                )
                            }
                        ) {
                            // 실제 UI를 그리기 위해 Composable 람다 호출
                            chatContent()
                        }
                    }
                }
            }
        }

        // 2. Dim 오버레이와 3. 선택된 아이템을 조건부로 최상위에 그립니다.
        if (isFocusMode && selectedItemLayout != null && selectedItemComposable != null) {
            // 2. 화면 전체를 덮는 반투명 Dim 레이어
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .pointerInput(Unit) {
                        // Dim 레이어를 탭하면 포커스 모드 해제
                        detectTapGestures(onTap = {
                            viewModel.processIntent(ChatIntent.ClearFocusMode)
                        })
                    }
            )

            // 3. Dim 레이어 위에 선택된 아이템만 다시 그립니다.
            with(LocalDensity.current) {
                Box(
                    modifier = Modifier
                        .offset(
                            x = selectedItemLayout!!.left.toDp(),
                            y = (selectedItemLayout!!.top - 179).toDp()
                        )
                        .size(
                            width = (selectedItemLayout!!.width).toDp(),
                            height = (selectedItemLayout!!.height).toDp()
                        )
                ) {
                    // 저장해둔 Composable 람다를 호출하여 아이템을 그림
                    selectedItemComposable!!()
                }
            }
        }

        if (showActionSheet) {
            GoriActionSheet(
                onDismissRequest = {
                    showActionSheet = false
                    viewModel.processIntent(ChatIntent.ClearFocusMode)
                },
                actions = listOfNotNull(
                    messageToCopy?.let { text ->
                        "복사하기" to {
                            clipboardManager.setText(AnnotatedString(text))
                            viewModel.processIntent(ChatIntent.ClearFocusMode)
                        }
                    }
                )
            )
        }
    }
}
