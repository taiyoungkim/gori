package com.team_gori.gori.feature_meeting.presentation

import androidx.compose.runtime.Immutable
import com.team_gori.gori.core.model.Category
import com.team_gori.gori.core.model.Meeting

enum class SortType { Latest, NearestDate }

@Immutable
data class MeetingUiState(
    val locationLabel: String = "서울 전체",
    val categories: List<Category> = Category.entries.toList(),
    val selectedCategory: Category? = null,
    val showRecommendationBanner: Boolean = true,
    val sortType: SortType = SortType.Latest,
    val items: List<Meeting> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed interface MeetingEvent {
    data object OnClickLocation : MeetingEvent
    data class OnSelectCategory(val category: Category?) : MeetingEvent // null = 전체
    data class OnChangeSort(val sortType: SortType) : MeetingEvent
    data class OnClickMeeting(val id: String) : MeetingEvent
    data object OnClickCreateMeeting : MeetingEvent
    data object OnDismissBanner : MeetingEvent
    data class OnClickChat(val id: String) : MeetingEvent
}

sealed interface MeetingEffect {
    data class NavigateDetail(val id: String) : MeetingEffect
    data object NavigateCreate : MeetingEffect
    data object OpenLocationPicker : MeetingEffect
    data class NavigateChat(val id: String) : MeetingEffect
    data class ShowError(val message: String) : MeetingEffect
}
