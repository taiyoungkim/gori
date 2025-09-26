package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NicknameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NicknameUiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<NicknameNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    fun onEvent(event: NicknameUiEvent) {
        when (event) {
            is NicknameUiEvent.ScreenEntered -> fetchRecommendedNickname()
            is NicknameUiEvent.NicknameChanged -> handleNicknameChange(event.nickname)
            is NicknameUiEvent.FocusChanged -> handleFocusChange(event.isFocused)
            is NicknameUiEvent.GetNewRecommendationClicked -> fetchRecommendedNickname()
            is NicknameUiEvent.NextClicked -> handleSubmit()
        }
    }

    private fun fetchRecommendedNickname() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, isEditing = false, userInput = "") }
            delay(500) // 가짜 네트워크 딜레이
            val newNickname = "따뜻하고맛있는" // 실제로는 API 호출
            _uiState.update { it.copy(isLoading = false, recommendedNickname = newNickname, error = null) }
        }
    }

    private fun handleNicknameChange(nickname: String) {
        // 8글자 초과 입력 방지
        if (nickname.length > 8) return

        _uiState.update { it.copy(userInput = nickname, error = null) }
    }

    private fun handleFocusChange(isFocused: Boolean) {
        // 포커스를 받았고, 아직 수정 모드가 아니라면 수정 모드로 전환
        if (isFocused && !_uiState.value.isEditing) {
            _uiState.update { it.copy(isEditing = true) }
        }
    }

    private fun handleSubmit() {
        val nicknameToValidate = _uiState.value.displayText

        // 1. 클라이언트 측 유효성 검사 (정규식)
        val regex = Regex("^[a-zA-Z0-9가-힣]*$")
        if (!regex.matches(nicknameToValidate)) {
            _uiState.update { it.copy(error = "한글, 영어, 숫자만 사용할 수 있어요.") }
            return
        }

        // 2. 서버 측 중복 검사 (가상)
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            delay(1000) // 가짜 네트워크 딜레이

            if (nicknameToValidate == "카페꼼마좋다") { // 중복 닉네임 예시
                _uiState.update { it.copy(isLoading = false, error = "누군가 사용중인가봐요. 다른 이름을 알려주세요.") }
            } else {
                _uiState.update { it.copy(isLoading = false, error = null) }
                _navEvent.emit(NicknameNavEvent.NavigateToNextScreen)
            }
        }
    }
}

/**
 * 닉네임 설정 화면의 UI 상태를 나타내는 데이터 클래스입니다.
 *
 * @param recommendedNickname 서버에서 받은 추천 닉네임
 * @param userInput 사용자가 직접 입력 중인 닉네임
 * @param isEditing 사용자가 추천 닉네임을 수정하기 시작했는지 여부
 * @param isLoading 비동기 작업(서버 통신 등) 중인지 여부
 * @param error 유효성 검사 실패 시 표시할 에러 메시지
 */
data class NicknameUiState(
    val recommendedNickname: String = "",
    val userInput: String = "",
    val isEditing: Boolean = false,
    val isLoading: Boolean = true,
    val error: String? = null
) {
    /**
     * TextField에 실제로 표시될 텍스트입니다.
     * 수정 중이면 userInput을, 아니면 recommendedNickname을 보여줍니다.
     */
    val displayText: String
        get() = if (isEditing) userInput else recommendedNickname

    /**
     * 하단 '다음' 버튼의 활성화 여부를 결정합니다.
     */
    val isNextButtonEnabled: Boolean
        get() = if (isEditing) {
            userInput.length >= 2 // 직접 입력 시 2글자 이상일 때 활성화
        } else {
            recommendedNickname.isNotEmpty() // 추천 닉네임은 항상 유효하다고 가정
        }
}

/**
 * UI(Screen)에서 ViewModel로 전달되는 이벤트입니다.
 */
sealed interface NicknameUiEvent {
    // 화면에 처음 진입했을 때
    data object ScreenEntered : NicknameUiEvent

    // TextField의 텍스트가 변경될 때
    data class NicknameChanged(val nickname: String) : NicknameUiEvent

    // TextField의 포커스 상태가 변경될 때
    data class FocusChanged(val isFocused: Boolean) : NicknameUiEvent

    // '딴거할래요' 버튼을 클릭했을 때
    data object GetNewRecommendationClicked : NicknameUiEvent

    // 하단 '다음' 버튼을 클릭했을 때
    data object NextClicked : NicknameUiEvent
}

/**
 * ViewModel에서 UI(Screen)로 전달되는 일회성 탐색 이벤트입니다.
 */
sealed interface NicknameNavEvent {
    // 닉네임 설정이 성공적으로 완료되었을 때
    data object NavigateToNextScreen : NicknameNavEvent
}
