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
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
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

private const val KEY_ROUTE = "route"
private const val KEY_RETURN_TAB = "returnTab"
private const val KEY_INITIAL_TAB = "initialTab"
private const val KEY_MEMBER_COUNT = "memberCount"
private const val KEY_MEETING_ID = "meetingId"
private const val KEY_ENTRY_TYPE = "entryType"
private const val KEY_ENTRY_TAB = "entryTab"

private const val ROUTE_MAIN = "main"
private const val ROUTE_DESIGN_GALLERY = "design_gallery"
private const val ROUTE_ADD_FEED = "add_feed"
private const val ROUTE_FEED_DETAIL = "feed_detail"
private const val ROUTE_ADD_CHAT_ROOM = "add_chat_room"
private const val ROUTE_CHAT_ROOM = "chat_room"
private const val ROUTE_CHAT_SETTING = "chat_setting"
private const val ROUTE_AGREEMENT_TERMS = "agreement_terms"
private const val ROUTE_SIGN_UP = "sign_up"
private const val ROUTE_INELIGIBLE = "ineligible"
private const val ROUTE_SIGN_UP_BLACK_LIST = "sign_up_black_list"
private const val ROUTE_VERIFY_CODE = "verify_code"
private const val ROUTE_NICKNAME = "nickname"
private const val ROUTE_PROFILE_IMAGE = "profile_image"
private const val ROUTE_SIGN_UP_COMPLETE = "sign_up_complete"
private const val ROUTE_CREATE_MEETING = "create_meeting"
private const val ROUTE_MEETING_DETAIL = "meeting_detail"

private const val ENTRY_SHOW_WELCOME = "show_welcome"
private const val ENTRY_NAVIGATE_TO = "navigate_to"

private val BooleanSaver = Saver<Boolean, Boolean>(
    save = { it },
    restore = { it ?: false }
)

private val MainTabSaver = Saver<MainTab, String>(
    save = { it.route },
    restore = { route -> route.toMainTabOrDefault() }
)

private val ScreenSaver = Saver<Screen, Map<String, String>>(
    save = { screen ->
        buildMap {
            put(KEY_ROUTE, screen.toRoute())
            when (screen) {
                is Screen.Main -> {
                    put(KEY_INITIAL_TAB, screen.initialTab.route)
                    screen.entry?.let { entry ->
                        put(KEY_ENTRY_TYPE, entry.toType())
                        entry.entryTabRoute()?.let { put(KEY_ENTRY_TAB, it) }
                    }
                }
                is Screen.DesignGallery -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.AddFeed -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.FeedDetail -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.AddChatRoom -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.ChatRoom -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.ChatSetting -> {
                    put(KEY_RETURN_TAB, screen.returnTab.route)
                    put(KEY_MEMBER_COUNT, screen.memberCount.toString())
                }
                is Screen.AgreementTerms -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.SignUp -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.Ineligible -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.SignUpBlackList -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.VerifyCode -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.Nickname -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.ProfileImage -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.SignUpComplete -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.CreateMeeting -> put(KEY_RETURN_TAB, screen.returnTab.route)
                is Screen.MeetingDetail -> {
                    put(KEY_RETURN_TAB, screen.returnTab.route)
                    put(KEY_MEETING_ID, screen.meetingId)
                }
            }
        }
    },
    restore = { saved ->
        val data = saved ?: return@Saver Screen.Main()
        when (data[KEY_ROUTE]) {
            ROUTE_MAIN -> Screen.Main(
                initialTab = data[KEY_INITIAL_TAB].toMainTabOrDefault(),
                entry = data.restoreEntry()
            )
            ROUTE_DESIGN_GALLERY -> data.returnTabOrNull()?.let { Screen.DesignGallery(returnTab = it) }
            ROUTE_ADD_FEED -> data.returnTabOrNull()?.let { Screen.AddFeed(returnTab = it) }
            ROUTE_FEED_DETAIL -> data.returnTabOrNull()?.let { Screen.FeedDetail(returnTab = it) }
            ROUTE_ADD_CHAT_ROOM -> data.returnTabOrNull()?.let { Screen.AddChatRoom(returnTab = it) }
            ROUTE_CHAT_ROOM -> data.returnTabOrNull()?.let { Screen.ChatRoom(returnTab = it) }
            ROUTE_CHAT_SETTING -> {
                val memberCount = data[KEY_MEMBER_COUNT]?.toIntOrNull()
                val returnTab = data.returnTabOrNull()
                if (memberCount != null && returnTab != null) {
                    Screen.ChatSetting(memberCount = memberCount, returnTab = returnTab)
                } else {
                    null
                }
            }
            ROUTE_AGREEMENT_TERMS -> data.returnTabOrNull()?.let { Screen.AgreementTerms(returnTab = it) }
            ROUTE_SIGN_UP -> data.returnTabOrNull()?.let { Screen.SignUp(returnTab = it) }
            ROUTE_INELIGIBLE -> data.returnTabOrNull()?.let { Screen.Ineligible(returnTab = it) }
            ROUTE_SIGN_UP_BLACK_LIST -> data.returnTabOrNull()?.let { Screen.SignUpBlackList(returnTab = it) }
            ROUTE_VERIFY_CODE -> data.returnTabOrNull()?.let { Screen.VerifyCode(returnTab = it) }
            ROUTE_NICKNAME -> data.returnTabOrNull()?.let { Screen.Nickname(returnTab = it) }
            ROUTE_PROFILE_IMAGE -> data.returnTabOrNull()?.let { Screen.ProfileImage(returnTab = it) }
            ROUTE_SIGN_UP_COMPLETE -> data.returnTabOrNull()?.let { Screen.SignUpComplete(returnTab = it) }
            ROUTE_CREATE_MEETING -> data.returnTabOrNull()?.let { Screen.CreateMeeting(returnTab = it) }
            ROUTE_MEETING_DETAIL -> {
                val meetingId = data[KEY_MEETING_ID]
                val returnTab = data.returnTabOrNull()
                if (meetingId != null && returnTab != null) {
                    Screen.MeetingDetail(returnTab = returnTab, meetingId = meetingId)
                } else {
                    null
                }
            }
            else -> null
        } ?: Screen.Main()
    }
)

private fun Map<String, String>.returnTabOrNull(): MainTab? = this[KEY_RETURN_TAB]?.toMainTab()

private fun Map<String, String>.restoreEntry(): MainEntry? {
    return when (this[KEY_ENTRY_TYPE]) {
        ENTRY_SHOW_WELCOME -> MainEntry.ShowWelcomeSheet
        ENTRY_NAVIGATE_TO -> this[KEY_ENTRY_TAB]?.toMainTab()?.let { MainEntry.NavigateTo(it) }
        else -> null
    }
}

private fun Screen.toRoute(): String = when (this) {
    is Screen.Main -> ROUTE_MAIN
    is Screen.DesignGallery -> ROUTE_DESIGN_GALLERY
    is Screen.AddFeed -> ROUTE_ADD_FEED
    is Screen.FeedDetail -> ROUTE_FEED_DETAIL
    is Screen.AddChatRoom -> ROUTE_ADD_CHAT_ROOM
    is Screen.ChatRoom -> ROUTE_CHAT_ROOM
    is Screen.ChatSetting -> ROUTE_CHAT_SETTING
    is Screen.AgreementTerms -> ROUTE_AGREEMENT_TERMS
    is Screen.SignUp -> ROUTE_SIGN_UP
    is Screen.Ineligible -> ROUTE_INELIGIBLE
    is Screen.SignUpBlackList -> ROUTE_SIGN_UP_BLACK_LIST
    is Screen.VerifyCode -> ROUTE_VERIFY_CODE
    is Screen.Nickname -> ROUTE_NICKNAME
    is Screen.ProfileImage -> ROUTE_PROFILE_IMAGE
    is Screen.SignUpComplete -> ROUTE_SIGN_UP_COMPLETE
    is Screen.CreateMeeting -> ROUTE_CREATE_MEETING
    is Screen.MeetingDetail -> ROUTE_MEETING_DETAIL
}

private fun MainEntry.toType(): String = when (this) {
    MainEntry.ShowWelcomeSheet -> ENTRY_SHOW_WELCOME
    is MainEntry.NavigateTo -> ENTRY_NAVIGATE_TO
}

private fun MainEntry.entryTabRoute(): String? = when (this) {
    MainEntry.ShowWelcomeSheet -> null
    is MainEntry.NavigateTo -> tab.route
}

private fun String?.toMainTabOrDefault(): MainTab = this?.toMainTab() ?: MainTab.Home

private fun String?.toMainTab(): MainTab? = when (this) {
    MainTab.Home.route -> MainTab.Home
    MainTab.Meeting.route -> MainTab.Meeting
    MainTab.Chatting.route -> MainTab.Chatting
    MainTab.Daily.route -> MainTab.Daily
    MainTab.MyActivity.route -> MainTab.MyActivity
    else -> null
}

@OptIn(ExperimentalMaterial3Api::class) // TopAppBar 사용 시
@Composable
fun App() {
    CustomTheme {
        var currentScreen: Screen by rememberSaveable(stateSaver = ScreenSaver) { mutableStateOf(Screen.Main()) }
        var currentMainTab by rememberSaveable(stateSaver = MainTabSaver) { mutableStateOf<MainTab>(MainTab.Home) }
        var showWelcome by rememberSaveable(stateSaver = BooleanSaver) { mutableStateOf(false) } // 예: 환영 시트 1회 露出

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