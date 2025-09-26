package com.team_gori.gori.feature_meeting.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.koin.compose.koinInject

/**
 * 화면 전환을 위한 sealed interface
 */
sealed class MeetingScreenRoute {
    data object CreateMeeting : MeetingScreenRoute()
    data object SelectLocation : MeetingScreenRoute()
}

@Composable
fun MeetingCreationNavHost(
    onFlowComplete: () -> Unit, // 모임 생성이 모두 완료되었을 때 호출
    onFlowExit: () -> Unit // 사용자가 중간에 X를 눌러 나갔을 때 호출
) {
    val createMeetingViewModel: CreateMeetingViewModel = koinInject()
    val locationViewModel: LocationViewModel = koinInject()

    var currentScreen by remember { mutableStateOf<MeetingScreenRoute>(MeetingScreenRoute.CreateMeeting) }

    when (currentScreen) {
        is MeetingScreenRoute.CreateMeeting -> {
            CreateMeetingScreen(
                viewModel = createMeetingViewModel,
                onClose = onFlowExit,
                onComplete = {
                    onFlowComplete()
                },
                onNavigateToLocation = {
                    currentScreen = MeetingScreenRoute.SelectLocation
                }
            )
        }
        is MeetingScreenRoute.SelectLocation -> {
            LocationScreen(
                viewModel = locationViewModel,
                onNavigateBack = {
                    // 뒤로가기 시 모임 생성 화면으로 복귀
                    currentScreen = MeetingScreenRoute.CreateMeeting
                },
                onComplete = { selectedLocation ->
                    // 완료 시, 선택된 지역 정보를 CreateMeetingViewModel에 전달하고 복귀
                    createMeetingViewModel.onEvent(CreateMeetingUiEvent.LocationSelected(selectedLocation))
                    currentScreen = MeetingScreenRoute.CreateMeeting
                }
            )
        }
    }
}

