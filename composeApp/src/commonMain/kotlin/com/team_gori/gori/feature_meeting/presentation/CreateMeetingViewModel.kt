package com.team_gori.gori.feature_meeting.presentation

import androidx.compose.ui.graphics.ImageBitmap
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
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.DrawableResource

class CreateMeetingViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CreateMeetingUiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<CreateMeetingNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    init {
        val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val roundedMinute = ((now.minute / 10) + 1) * 10
        var initialHour = now.hour
        var initialMinute = roundedMinute
        if (roundedMinute >= 60) {
            initialHour = (now.hour + 1) % 24
            initialMinute = 0
        }

        val initialTime = LocalTime(initialHour, initialMinute)

        _uiState.update {
            it.copy(
                selectedDate = today,
                selectedTime = initialTime
            )
        }
    }

    fun onEvent(event: CreateMeetingUiEvent) {
        when (event) {
            is CreateMeetingUiEvent.ScreenEntered -> loadInitialData()
            is CreateMeetingUiEvent.NextClicked -> handleNextClick()
            is CreateMeetingUiEvent.BackClicked -> handleBackClick()
            is CreateMeetingUiEvent.MeetingNameChanged -> _uiState.update { it.copy(meetingName = event.name) }
            is CreateMeetingUiEvent.CategorySelected -> _uiState.update { it.copy(selectedCategoryId = event.categoryId) }
            is CreateMeetingUiEvent.CoverImageSelected -> _uiState.update { it.copy(selectedImageBitmap = event.uri) }
            is CreateMeetingUiEvent.CategoryResource -> _uiState.update { it.copy(selectedCategoryResource = event.categoryResource) }
            // 2단계 이벤트 처리
            is CreateMeetingUiEvent.DateSelected -> {
                _uiState.update { it.copy(selectedDate = event.date, showDatePicker = false, isDateSetByUser = true) }
            }
            is CreateMeetingUiEvent.TimeSelected -> {
                _uiState.update { it.copy(selectedTime = event.time, showTimePicker = false) }
            }
            is CreateMeetingUiEvent.LocationSelected -> {
                _uiState.update { it.copy(selectedLocation = event.location, showLocationPicker = false, isLocationSetByUser = true) }
            }
            is CreateMeetingUiEvent.ShowDialog -> {
                when(event.type) {
                    DialogType.DATE -> _uiState.update { it.copy(showDatePicker = event.show) }
                    DialogType.TIME -> _uiState.update { it.copy(showTimePicker = event.show) }
                    DialogType.LOCATION -> _uiState.update { it.copy(showLocationPicker = event.show) }
                }
            }
            is CreateMeetingUiEvent.TimeConfirmed -> {
                _uiState.update { it.copy(selectedTime = event.time, isTimeUndecided = event.isUndecided, isTimeSetByUser = true) }
            }
            is CreateMeetingUiEvent.LocationRowClicked -> {
                viewModelScope.launch {
                    _navEvent.emit(CreateMeetingNavEvent.NavigateToLocation)
                }
            }
            is CreateMeetingUiEvent.MaxParticipantsChanged -> {
                val newCount = event.count.coerceIn(2, 50) // 최소 2명, 최대 50명 등 제한
                _uiState.update { it.copy(maxParticipants = newCount) }
            }
            is CreateMeetingUiEvent.GenderSelected -> {
                _uiState.update { it.copy(selectedGender = event.gender) }
            }
            is CreateMeetingUiEvent.AgeSelected -> {
                _uiState.update { it.copy(selectedAge = event.age) }
            }
            is CreateMeetingUiEvent.JoinMethodSelected -> {
                _uiState.update { it.copy(selectedJoinMethod = event.method) }
            }
            is CreateMeetingUiEvent.CustomAgeChanged -> {
                _uiState.update { it.copy(customAgeRange = event.age) }
            }

            is CreateMeetingUiEvent.DescriptionChanged -> {
                _uiState.update { it.copy(meetingDescription = event.description) }
            }
        }
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            // 실제 앱에서는 서버에서 카테고리 목록을 가져옵니다.
            val categories = listOf(
                MeetingCategory("1", "건강/운동", Res.drawable.health_fitness),
                MeetingCategory("2", "맛집/카페", Res.drawable.good_place),
                MeetingCategory("3", "나들이/여행", Res.drawable.trip),
                MeetingCategory("4", "재테크", Res.drawable.health_fitness),
                MeetingCategory("5", "취미", Res.drawable.hobby),
                MeetingCategory("6", "기타", Res.drawable.health_fitness)
            )
            _uiState.update { it.copy(categories = categories) }
        }
    }

    private fun handleNextClick() {
        val currentState = _uiState.value

        if (currentState.currentStep == CreateMeetingStep.STEP4) {
            if (currentState.isNextButtonEnabled) {
                // 유효성 검사 통과 -> 모임 생성 시도
                createMeeting()
            } else {
                // 유효성 검사 실패 -> 에러 메시지 표시
                _uiState.update { it.copy(descriptionError = "모임설명은 3글자 이상 입력해주세요.") }
            }
            return
        }

        val currentStep = _uiState.value.currentStep
        if (!_uiState.value.isNextButtonEnabled) return

        if (currentStep.ordinal < CreateMeetingStep.entries.lastIndex) {
            val nextStep = CreateMeetingStep.entries[currentStep.ordinal + 1]
            _uiState.update { it.copy(currentStep = nextStep) }
        } else {
            // 마지막 단계에서 '완료' 로직 수행
            createMeeting()
        }
    }

    private fun handleBackClick() {
        val currentStep = _uiState.value.currentStep
        if (currentStep.ordinal > 0) {
            val prevStep = CreateMeetingStep.entries[currentStep.ordinal - 1]
            _uiState.update { it.copy(currentStep = prevStep) }
        } else {
            // 첫 단계에서 뒤로가기 시 화면 닫기
            viewModelScope.launch { _navEvent.emit(CreateMeetingNavEvent.CloseScreen) }
        }
    }

    private fun createMeeting() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1500) // 가짜 서버 통신
            // ... 실제 모임 생성 로직 ...
            _uiState.update { it.copy(isLoading = false) }
            _navEvent.emit(CreateMeetingNavEvent.CreationComplete("new_meeting_id_123"))
        }
    }
}

/**
 * 모임 생성을 위한 단계를 정의합니다.
 */
enum class CreateMeetingStep(val title: String) {
    STEP1("기본 정보"),
    STEP2("장소 및 시간"),
    STEP3("시간 설정"),
    STEP4("추가 정보")
}

/**
 * 모임 카테고리 항목을 나타내는 데이터 클래스입니다.
 */
data class MeetingCategory(
    val id: String,
    val name: String,
    val img: DrawableResource,
)

enum class GenderOption(val displayName: String) {
    ANY("누구나"), FEMALE_ONLY("여자만")
}

enum class AgeOption(val displayName: String) {
    ANY("누구나"), FIFTIES("50대"), SIXTIES("60대"), SEVENTIES("70대"), EIGHTIES("80대"), CUSTOM("직접입력")
}

enum class JoinMethod(val displayName: String) {
    ANYONE("누구나 가입"), APPROVAL("방장 승인")
}

/**
 * 모임 생성 화면 전체의 UI 상태를 나타냅니다.
 */
data class CreateMeetingUiState(
    // 진행 단계
    val currentStep: CreateMeetingStep = CreateMeetingStep.STEP1,

    // 1단계: 기본 정보
    val meetingName: String = "",
    val categories: List<MeetingCategory> = emptyList(),
    val selectedCategoryId: String? = null,
    val selectedCategoryName: String = "",
    val selectedCategoryResource: DrawableResource? = null,
    val selectedImageBitmap: ImageBitmap? = null,

    // 2단계: 장소 및 시간
    val selectedDate: LocalDate? = null,
    val selectedTime: LocalTime? = null,
    val selectedLocation: String = "미정",
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val showLocationPicker: Boolean = false,
    /** 시간 '미정' 옵션 선택 여부입니다. */
    val isTimeUndecided: Boolean = false,
    val isDateSetByUser: Boolean = false,
    val isTimeSetByUser: Boolean = false,
    val isLocationSetByUser: Boolean = false,

    val maxParticipants: Int = 10,
    val selectedGender: GenderOption = GenderOption.ANY,
    val selectedAge: AgeOption = AgeOption.ANY,
    val selectedJoinMethod: JoinMethod = JoinMethod.ANYONE,
    val customAgeRange: Int = 50,

    val meetingDescription: String = "",
    val descriptionError: String? = null,
    // 로딩 상태
    val isLoading: Boolean = false
) {

    /**
     * 현재 단계의 입력값이 유효한지 판단하여 '다음' 버튼의 활성화 상태를 결정합니다.
     */
    val isNextButtonEnabled: Boolean
        get() = when (currentStep) {
            CreateMeetingStep.STEP1 ->
                meetingName.isNotBlank() && selectedCategoryId != null
            CreateMeetingStep.STEP2 -> true
            CreateMeetingStep.STEP3 -> true
            CreateMeetingStep.STEP4 -> meetingDescription.length >= 3
        }
}


/**
 * UI(Screen)에서 ViewModel로 전달되는 이벤트입니다.
 */
sealed interface CreateMeetingUiEvent {
    // 화면에 처음 진입했을 때 데이터 로딩을 요청
    data object ScreenEntered : CreateMeetingUiEvent

    // '다음' 버튼 클릭
    data object NextClicked : CreateMeetingUiEvent

    // '뒤로가기' 또는 'X' 버튼 클릭
    data object BackClicked : CreateMeetingUiEvent

    // 1단계 이벤트
    data class MeetingNameChanged(val name: String) : CreateMeetingUiEvent
    data class CategorySelected(val categoryId: String) : CreateMeetingUiEvent
    data class CategoryResource(val categoryResource: DrawableResource) : CreateMeetingUiEvent
    data class CoverImageSelected(val uri: ImageBitmap?) : CreateMeetingUiEvent

    // 2단계 이벤트
    data class TimeSelected(val time: LocalTime) : CreateMeetingUiEvent
    data class ShowDialog(val type: DialogType, val show: Boolean) : CreateMeetingUiEvent
    /** 날짜를 선택했을 때 호출됩니다. */
    data class DateSelected(val date: LocalDate) : CreateMeetingUiEvent
    /** 시간 선택 바텀시트에서 '완료'를 눌렀을 때 호출됩니다. */
    data class TimeConfirmed(val time: LocalTime?, val isUndecided: Boolean) : CreateMeetingUiEvent
    /** 장소를 선택했을 때 호출됩니다. */
    data class LocationSelected(val location: String) : CreateMeetingUiEvent
    data object LocationRowClicked : CreateMeetingUiEvent

    data class MaxParticipantsChanged(val count: Int) : CreateMeetingUiEvent
    data class GenderSelected(val gender: GenderOption) : CreateMeetingUiEvent
    data class AgeSelected(val age: AgeOption) : CreateMeetingUiEvent
    data class JoinMethodSelected(val method: JoinMethod) : CreateMeetingUiEvent
    data class CustomAgeChanged(val age: Int) : CreateMeetingUiEvent

    data class DescriptionChanged(val description: String) : CreateMeetingUiEvent
}

enum class DialogType {
    DATE, TIME, LOCATION
}
/**
 * ViewModel에서 UI(Screen)로 전달되는 일회성 탐색 이벤트입니다.
 */
sealed interface CreateMeetingNavEvent {
    // 모임 생성을 취소하고 화면을 닫아야 할 때
    data object CloseScreen : CreateMeetingNavEvent

    // 모임 생성이 완료되었을 때
    data class CreationComplete(val meetingId: String) : CreateMeetingNavEvent

    data object NavigateToLocation : CreateMeetingNavEvent
}
