package com.team_gori.gori.feature_login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import gori.composeapp.generated.resources.Res
import gori.composeapp.generated.resources.good_place
import gori.composeapp.generated.resources.health_fitness
import gori.composeapp.generated.resources.hobby
import gori.composeapp.generated.resources.trip
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource

class InterestViewModel : ViewModel() { // Multiplatform ViewModel 라이브러리 사용 가정

    private val _uiState = MutableStateFlow(InterestUiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<InterestNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    fun onEvent(event: InterestUiEvent) {
        when (event) {
            is InterestUiEvent.ScreenEntered -> loadTopics()
            is InterestUiEvent.TopicClicked -> toggleTopicSelection(event.topicId)
            is InterestUiEvent.SaveClicked -> saveInterests()
        }
    }

    private fun loadTopics() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(500) // 가짜 네트워크 딜레이
            val topics = listOf(
                InterestTopic(1, "건강/운동", Res.drawable.health_fitness),
                InterestTopic(2, "맛집/카페", Res.drawable.good_place),
                InterestTopic(3, "나들이/여행", Res.drawable.trip),
                InterestTopic(4, "취미", Res.drawable.hobby),
                InterestTopic(5, "재테크", Res.drawable.health_fitness),
                InterestTopic(6, "일상", Res.drawable.health_fitness)
            )
            _uiState.update { it.copy(isLoading = false, topics = topics) }
        }
    }

    private fun toggleTopicSelection(topicId: Int) {
        val currentSelectedIds = _uiState.value.selectedTopicIds
        val newSelectedIds = if (topicId in currentSelectedIds) {
            currentSelectedIds - topicId
        } else {
            currentSelectedIds + topicId
        }
        _uiState.update { it.copy(selectedTopicIds = newSelectedIds) }
    }

    private fun saveInterests() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000) // 가짜 저장 로직 딜레이
            // ... 실제 저장 로직 ...
            _uiState.update { it.copy(isLoading = false) }
            _navEvent.emit(InterestNavEvent.CloseScreen)
        }
    }
}

/**
 * 개별 관심 주제 항목을 나타내는 데이터 클래스입니다.
 * @param id 고유 식별자
 * @param title 주제 이름 (예: "건강/운동")
 * @param imageRes 이미지 리소스 ID (실제 프로젝트에서는 URL 등이 될 수 있음)
 */
data class InterestTopic(
    val id: Int,
    val title: String,
     val imageRes: DrawableResource
)

/**
 * 관심 주제 선택 화면 전체의 UI 상태를 나타냅니다.
 */
data class InterestUiState(
    val topics: List<InterestTopic> = emptyList(),
    val selectedTopicIds: Set<Int> = emptySet(),
    val isLoading: Boolean = true
) {
    /**
     * '저장하기' 버튼의 활성화 여부를 결정합니다.
     * 하나 이상의 주제가 선택되었을 때 활성화됩니다.
     */
    val isSaveButtonEnabled: Boolean
        get() = selectedTopicIds.isNotEmpty()
}
/**
 * UI(Screen)에서 ViewModel로 전달되는 이벤트입니다.
 */
sealed interface InterestUiEvent {
    // 화면에 처음 진입했을 때 데이터 로딩을 요청
    data object ScreenEntered : InterestUiEvent

    // 사용자가 특정 주제를 클릭했을 때
    data class TopicClicked(val topicId: Int) : InterestUiEvent

    // '저장하기' 버튼을 클릭했을 때
    data object SaveClicked : InterestUiEvent
}

/**
 * ViewModel에서 UI(Screen)로 전달되는 일회성 탐색 이벤트입니다.
 */
sealed interface InterestNavEvent {
    // 저장이 완료되어 화면을 닫아야 할 때
    data object CloseScreen : InterestNavEvent
}
