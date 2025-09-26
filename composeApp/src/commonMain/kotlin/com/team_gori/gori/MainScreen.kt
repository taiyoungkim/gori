package com.team_gori.gori

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.team_gori.gori.create.presentation.CreateScreen
import com.team_gori.gori.designsystem.component.GoriExtendedPillButton
import com.team_gori.gori.designsystem.theme.semanticColors
import com.team_gori.gori.feature_login.presentation.InterestScreen
import com.team_gori.gori.feature_meeting.presentation.MeetingEffect
import com.team_gori.gori.feature_meeting.presentation.MeetingRoute
import com.team_gori.gori.home.presentation.HomeScreen
import com.team_gori.gori.home.presentation.model.MainTab
import com.team_gori.gori.my_page.presentation.MyPageScreen
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    currentTab: MainTab,
    showWelcome: Boolean,
    onWelcomeDismissed: () -> Unit,
    onTabSelected: (MainTab) -> Unit,
    onNavigateToGallery: () -> Unit,
    onNavigateToAddFeed: () -> Unit,
    onNavigateToAddChatRoom: () -> Unit,
    onNavigateToAddMeetingRoom: () -> Unit,
    onNavigateToChatRoom: () -> Unit,
    onNavigateToFeedDetail: () -> Unit,
    onNavigateToMeetingDetail: (String) -> Unit,
) {

    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(showWelcome) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val items = listOf(
        MainTab.Home,
        MainTab.Daily,
        MainTab.Meeting,
        MainTab.Chatting,
        MainTab.MyActivity,
    )

    Scaffold(
        modifier = modifier,
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.semanticColors.backgroundNormal,
                modifier = Modifier.background(MaterialTheme.semanticColors.backgroundNormal),
            ) {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentTab == item,
                        onClick = { onTabSelected(item) },
                        icon = { Icon(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(item.icon),
                            contentDescription = item.title
                        ) },
                        label = { Text(item.title) },
                        alwaysShowLabel = true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.semanticColors.labelNormal,     // 선택된 아이콘 색상
                            unselectedIconColor = Color.Gray,    // 선택되지 않은 아이콘 색상
                            selectedTextColor = MaterialTheme.semanticColors.labelNormal,     // 선택된 텍스트 색상
                            unselectedTextColor = Color.Gray,    // 선택되지 않은 텍스트 색상
                            indicatorColor = Color.Transparent // 선택 시 나타나는 인디케이터 색상 (필요 없다면 투명으로)
                        )
                    )
                }
            }
        },
        floatingActionButton = {
            if (currentTab == MainTab.Daily) {
                GoriExtendedPillButton(
                    "피드 만들기",
                    onClick = onNavigateToAddFeed
                )
            } else if (currentTab == MainTab.Chatting) {
                GoriExtendedPillButton(
                    "채팅 만들기",
                    onClick = onNavigateToAddChatRoom
                )
            } else if (currentTab == MainTab.Meeting) {
                GoriExtendedPillButton(
                    "모임 만들기",
                    onClick = onNavigateToAddMeetingRoom
                )
            }
            // 다른 탭에서는 FAB이 필요 없으면 이 부분은 비워둠
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            when (currentTab) {
                MainTab.Home -> HomeScreen(onNavigateToChatRoom = onNavigateToChatRoom)
                MainTab.Daily -> CreateScreen(onNavigateToFeedDetail = onNavigateToFeedDetail)
                MainTab.Meeting -> {
                    Box(Modifier.windowInsetsPadding(WindowInsets.safeDrawing)) {
                        MeetingRoute(
                            onEffect = { effect ->
                                when (effect) {
                                    is MeetingEffect.NavigateDetail -> {
                                        onNavigateToMeetingDetail(effect.id)
                                    }

                                    MeetingEffect.NavigateCreate -> {
                                        // "모임 만들기" FAB → 모임 작성 화면
                                        onNavigateToAddMeetingRoom()
                                    }

                                    is MeetingEffect.NavigateChat -> {
                                        // 채팅방 이동 (현재 onNavigateToChatRoom은 id 파라미터가 없음)
                                        // 임시로 호출만 하고, 실제 라우팅에서 최근 선택 id를 읽는 구조면 OK
                                        // 권장: onNavigateToChatRoom(effect.id) 형태로 시그니처 확장
                                        onNavigateToChatRoom()
                                    }

                                    MeetingEffect.OpenLocationPicker -> {
                                        // 위치 선택 바텀시트/화면 열기 (없으면 나중에 연결)
                                        // TODO: openLocationPicker()
                                    }

                                    is MeetingEffect.ShowError -> {
                                        // TODO: Snackbar 등으로 노출
                                    }
                                }
                            }
                        )
                    }
                }
                MainTab.Chatting -> CreateScreen(onNavigateToFeedDetail = onNavigateToFeedDetail)
                MainTab.MyActivity -> MyPageScreen()
            }
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = MaterialTheme.semanticColors.backgroundNormal,
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) showBottomSheet = false
                }
            },
            sheetState = sheetState
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight(0.9f)
                    .background(color = MaterialTheme.semanticColors.backgroundNormal)
            ) {
                InterestScreen(
                    onClose = {
                        scope.launch {
                            sheetState.hide()
                        }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        }
                    }
                )
            }
        }
    }
}