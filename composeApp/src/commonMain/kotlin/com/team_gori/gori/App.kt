package com.team_gori.gori

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.team_gori.gori.designsystem.theme.CustomTheme
import com.team_gori.gori.designsystem.theme.LabelNormal
import com.team_gori.gori.feature_chat.presentation.AddChatRoomScreen
import com.team_gori.gori.feature_chat.presentation.ChatRoomScreen
import com.team_gori.gori.feature_chat.presentation.ChatSettingScreen
import com.team_gori.gori.feature_login.presentation.AgreementTermsServiceScreen
import com.team_gori.gori.feature_login.presentation.LoginScreen
import com.team_gori.gori.feature_login.presentation.sign_up.IneligibleScreen
import com.team_gori.gori.feature_login.presentation.sign_up.NicknameScreen
import com.team_gori.gori.feature_login.presentation.sign_up.ProfileImageScreen
import com.team_gori.gori.feature_login.presentation.sign_up.SignUpBlackListScreen
import com.team_gori.gori.feature_login.presentation.sign_up.SignUpCompleteScreen
import com.team_gori.gori.feature_login.presentation.sign_up.SignUpScreen
import com.team_gori.gori.feature_login.presentation.sign_up.VerifyCodeScreen
import com.team_gori.gori.feature_meeting.presentation.MeetingCreationNavHost
import com.team_gori.gori.feature_meeting.presentation.MeetingDetailEffect
import com.team_gori.gori.feature_meeting.presentation.MeetingDetailRoute
import com.team_gori.gori.gallery.DesignSystemGalleryScreen
import com.team_gori.gori.home.presentation.model.MainTab
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.ic_settings
import org.jetbrains.compose.resources.painterResource

sealed interface Screen {
    data class Main(
        val initialTab: MainTab = MainTab.Home,
        val entry: MainEntry? = null
    ) : Screen

    data class DesignGallery(val returnTab: MainTab) : Screen
    data class AddFeed(val returnTab: MainTab) : Screen
    data class FeedDetail(val returnTab: MainTab) : Screen
    data class AddChatRoom(val returnTab: MainTab) : Screen
    data class ChatRoom(val returnTab: MainTab) : Screen
    data class ChatSetting(val memberCount: Int, val returnTab: MainTab) : Screen
    data class AgreementTerms(val returnTab: MainTab) : Screen
    data class SignUp(val returnTab: MainTab) : Screen
    data class Ineligible(val returnTab: MainTab) : Screen
    data class SignUpBlackList(val returnTab: MainTab) : Screen
    data class VerifyCode(val returnTab: MainTab) : Screen
    data class Nickname(val returnTab: MainTab) : Screen
    data class ProfileImage(val returnTab: MainTab) : Screen
    data class SignUpComplete(val returnTab: MainTab) : Screen
    data class CreateMeeting(val returnTab: MainTab) : Screen
    data class MeetingDetail(
        val returnTab: MainTab,
        val meetingId: String
    ) : Screen
}

/** 메인 진입 시 1회성으로 실행할 행동들 */
sealed interface MainEntry {
    data object ShowWelcomeSheet : MainEntry
    data class NavigateTo(val tab: MainTab) : MainEntry
}

@OptIn(ExperimentalMaterial3Api::class) // TopAppBar 사용 시
@Composable
fun App() {
    CustomTheme {
        var currentScreen: Screen by remember { mutableStateOf(Screen.Main()) }
        var currentMainTab by remember { mutableStateOf<MainTab>(MainTab.Home) }
        var showWelcome by remember { mutableStateOf(false) } // 예: 환영 시트 1회 露出

        when (val screen = currentScreen) {
            is Screen.Main -> {
                LaunchedEffect(screen.initialTab, screen.entry) {
                    currentMainTab = screen.initialTab
                    when (val e = screen.entry) {
                        is MainEntry.ShowWelcomeSheet -> {
                            showWelcome = true      // 메인에서 1회성으로 바텀시트/다이얼로그 열기
                        }
                        is MainEntry.NavigateTo -> {
                            currentMainTab = e.tab  // 필요하면 탭 덮어쓰기
                        }
                        null -> Unit
                    }
                }

                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = currentMainTab.title,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = LabelNormal,
                                )
                            },
                            actions = {
                                IconButton(onClick = { currentScreen = Screen.DesignGallery(returnTab = currentMainTab) }) {
                                    Image(
                                        painter = painterResource(Res.drawable.ic_settings),
                                        contentDescription = "avatar",
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        )
                    }
                ) { paddingValues ->
                    MainScreen(
                        modifier = Modifier.padding(paddingValues),
                        currentTab = currentMainTab,
                        onTabSelected = { currentMainTab = it },
                        // ↓ 메인에서 특정 화면을 1회 띄우고 나면 콜백으로 닫아 상태 초기화
                        showWelcome = showWelcome,
                        onWelcomeDismissed = { showWelcome = false },
                        onNavigateToGallery = {
                            currentScreen = Screen.DesignGallery(returnTab = currentMainTab)
                        },
                        onNavigateToAddFeed = {
                            currentScreen = Screen.AddFeed(returnTab = currentMainTab)
                        },
                        onNavigateToAddChatRoom = {
                            currentScreen = Screen.AddChatRoom(returnTab = currentMainTab)
                        },
                        onNavigateToAddMeetingRoom = {
                            currentScreen = Screen.CreateMeeting(returnTab = currentMainTab)
                        },
                        onNavigateToChatRoom = {
                            currentScreen = Screen.ChatRoom(returnTab = currentMainTab)
                        },
                        onNavigateToFeedDetail = {
                            currentScreen = Screen.FeedDetail(returnTab = currentMainTab)
                        },
                        onNavigateToMeetingDetail = { id ->
                            currentScreen = Screen.MeetingDetail(
                                returnTab = currentMainTab,
                                meetingId = id
                            )
                        },
                    )
                }
            }
            is Screen.DesignGallery -> {
                DesignSystemGalleryScreen(
                    onNavigateBack = { currentScreen = Screen.Main(initialTab = screen.returnTab) }
                )
            }
            is Screen.AddFeed -> {
//                AddFeedScreen {
//                    onNavigateBack = { currentScreen = Screen.Main(initialTab = screen.returnTab) }
//                }
                LoginScreen(
                    onNavigateToAgreementTermsScreen = {
                        currentScreen = Screen.AgreementTerms(returnTab = currentMainTab)
                    }
                )
            }
            is Screen.AddChatRoom -> {
                AddChatRoomScreen(
                    onNavigateBack = { currentScreen = Screen.Main(initialTab = screen.returnTab) }
                )
            }
            is Screen.ChatRoom -> {
                ChatRoomScreen(
                    onNavigateBack = { currentScreen = Screen.Main(initialTab = screen.returnTab) },
                    onNavigateToChatSetting = { memberCount ->
                        currentScreen = Screen.ChatSetting(memberCount, returnTab = currentMainTab)
                    },
                )
            }
            is Screen.ChatSetting -> {
                ChatSettingScreen(
                    memberCount = screen.memberCount,
                    onNavigateBack = { currentScreen = Screen.ChatRoom(returnTab = currentMainTab) }
                )
            }
            is Screen.FeedDetail -> {

            }
            is Screen.AgreementTerms -> {
                AgreementTermsServiceScreen(
                    onNavigateBack = { currentScreen = Screen.Main(initialTab = screen.returnTab) },
                    onNavigateToSignUp = { currentScreen = Screen.SignUp(returnTab = currentMainTab) }
                )
            }
            is Screen.SignUp -> {
                SignUpScreen(
                    onNavigateBack = { currentScreen = Screen.AgreementTerms(returnTab = currentMainTab) },
                    onNavigateToIneligible = { currentScreen = Screen.Ineligible(returnTab = currentMainTab) },
                    onNavigateBlackList = { currentScreen = Screen.SignUpBlackList(returnTab = currentMainTab) },
                    onNavigateToVerifyCode = { currentScreen = Screen.VerifyCode(returnTab = currentMainTab) }
                )
            }
            is Screen.Ineligible -> {
                IneligibleScreen(
                    onClose = { currentScreen = Screen.Main(initialTab = screen.returnTab) }
                )
            }
            is Screen.SignUpBlackList -> {
                SignUpBlackListScreen(
                    onClose = { currentScreen = Screen.Main(initialTab = screen.returnTab) }
                )
            }
            is Screen.VerifyCode -> {
                VerifyCodeScreen(
                    onNavigateBack = { currentScreen = Screen.AgreementTerms(returnTab = currentMainTab) },
                    onComplete = { currentScreen = Screen.Nickname(returnTab = currentMainTab) },
                )
            }
            is Screen.Nickname -> {
                NicknameScreen(
                    onNavigateBack = { currentScreen = Screen.Main(initialTab = screen.returnTab) },
                    onNavigateNext = { currentScreen = Screen.ProfileImage(returnTab = currentMainTab) }
                )
            }
            is Screen.ProfileImage -> {
                ProfileImageScreen(
                    onNavigateBack = { currentScreen = Screen.Main(initialTab = screen.returnTab) },
                    onNavigateNext = { currentScreen = Screen.SignUpComplete(returnTab = currentMainTab) }
                )
            }
            is Screen.SignUpComplete -> {
                SignUpCompleteScreen(
                    onClose = {
                        currentScreen = Screen.Main(
                            initialTab = MainTab.Home,
                            entry = MainEntry.ShowWelcomeSheet
                        )
                    },
                )
            }
            is Screen.CreateMeeting -> {
                MeetingCreationNavHost(
                    onFlowExit = { currentScreen = Screen.Main(initialTab = screen.returnTab) },
                    onFlowComplete = {
                        // TODO: 모임 생성 완료 후 로직 (예: 생성된 모임 상세 화면으로 이동 또는 메인으로 복귀)
                        currentScreen = Screen.Main(initialTab = screen.returnTab)
                    }
                )
            }
            is Screen.MeetingDetail -> {
                MeetingDetailRoute(
                    onEffect = { if (it is MeetingDetailEffect.NavigateBack) currentScreen = Screen.Main(initialTab = screen.returnTab) },
//                    onEffect = { eff ->
//                        when (eff) {
//                            MeetingDetailEffect.NavigateBack ->
//                                currentScreen = Screen.Main(initialTab = screen.returnTab)
//
//                            is MeetingDetailEffect.Share -> {
//                                // TODO: 공유 처리
//                            }
//
//                            is MeetingDetailEffect.ShowMoreMenu -> {
//                                // TODO: 더보기 처리
//                            }
//                        }
//                    },
                    meetingId = screen.meetingId,
                )
            }
        }
    }
}