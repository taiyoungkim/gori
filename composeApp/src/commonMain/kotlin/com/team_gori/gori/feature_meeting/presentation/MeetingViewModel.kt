package com.team_gori.gori.feature_meeting.presentation

import com.team_gori.gori.core.model.Meeting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MeetingViewModel(
    private val scope: CoroutineScope = MainScope()
) {
    private val _state = MutableStateFlow(
        MeetingUiState(
            items = sampleMeetings()
        )
    )
    val state: StateFlow<MeetingUiState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MeetingEffect>()
    val effect = _effect

    fun onEvent(event: MeetingEvent) {
        when (event) {
            is MeetingEvent.OnClickLocation -> scope.launch { _effect.emit(MeetingEffect.OpenLocationPicker) }
            is MeetingEvent.OnSelectCategory -> _state.value = _state.value.copy(selectedCategory = event.category)
            is MeetingEvent.OnChangeSort -> _state.value = _state.value.copy(sortType = event.sortType)
            is MeetingEvent.OnClickMeeting -> scope.launch { _effect.emit(MeetingEffect.NavigateDetail(event.id)) }
            MeetingEvent.OnClickCreateMeeting -> scope.launch { _effect.emit(MeetingEffect.NavigateCreate) }
            MeetingEvent.OnDismissBanner -> _state.value = _state.value.copy(showRecommendationBanner = false)
            is MeetingEvent.OnClickChat -> scope.launch { _effect.emit(MeetingEffect.NavigateChat(event.id)) }
        }
    }

    fun clear() { scope.cancel() }
}

private fun sampleMeetings() = listOf(
    Meeting("1","내일, 오후 12시","토요일 아침 파크골프 팀원 모집","https://picsum.photos/seed/1/200/200",4,6,"노원구"),
    Meeting("2","8월 16일 (토), 오전 10시 30분","5060 청춘 모여라! 관악산 등반","https://picsum.photos/seed/2/200/200",3,10,"관악구"),
    Meeting("3","8월 9일 (토), 오후 9시","와인을 사랑하는 시음회","https://picsum.photos/seed/3/200/200",6,6,"중랑구"),
    Meeting("4","7월 31일 (목), 오후 2시 30분","문학의 밤","https://picsum.photos/seed/4/200/200",4,6,"노원구")
)
