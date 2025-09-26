package com.team_gori.gori.feature_meeting.presentation

import com.team_gori.gori.core.model.MeetingDetail

data class MeetingDetailUiState(
    val titleOnAppBar: String = "",
    val content: Content = Content.Loading
) {
    sealed interface Content {
        data object Loading : Content
        data class Success(val data: MeetingDetail) : Content
        data class Error(val message: String) : Content
    }
}

sealed interface MeetingDetailEvent {
    data object OnClickBack : MeetingDetailEvent
    data object OnClickShare : MeetingDetailEvent
    data object OnClickMore : MeetingDetailEvent
    data object OnRetryLoad : MeetingDetailEvent
    data object OnClickJoin : MeetingDetailEvent
    data object OnClickSeeAllMembers : MeetingDetailEvent
    data object OnClickSeeWaitingMembers : MeetingDetailEvent
}

sealed interface MeetingDetailEffect {
    data object NavigateBack : MeetingDetailEffect
    data class Share(val id: String) : MeetingDetailEffect
    data class ShowMoreMenu(val id: String) : MeetingDetailEffect
    data object OpenAllMembers : MeetingDetailEffect
    data object OpenWaitingMembers : MeetingDetailEffect
    data object RequestJoin : MeetingDetailEffect
}
