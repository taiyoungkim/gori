package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class VerifyCodeViewModel(
    private val expectedCode: String = "123456" // 실제로는 외부에서 주입받는 것이 좋음
) : ViewModel() {

    private val _uiState = MutableStateFlow(VerifyCodeUiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<VerifyCodeNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    private var timerJob: Job? = null
    private val durationMillis: Long = 5 * 60_000L

    fun onEvent(event: VerifyCodeUiEvent) {
        when (event) {
            is VerifyCodeUiEvent.CodeChanged -> handleCodeInput(event.code)
            is VerifyCodeUiEvent.NextClicked -> verifyCode()
            is VerifyCodeUiEvent.ResendClicked -> startTimer()
            is VerifyCodeUiEvent.ScreenEntered -> startTimer()
        }
    }

    private fun startTimer() {
        timerJob?.cancel() // 기존 타이머가 있다면 취소

        val endTime = kotlinx.datetime.Clock.System.now().toEpochMilliseconds() + durationMillis

        timerJob = viewModelScope.launch {
            _uiState.update { it.copy(isResendEnabled = false, isCountingDown = true) }

            while (isActive) {
                val now = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
                val remainingMillis = (endTime - now).coerceAtLeast(0L)
                val remainingSeconds = remainingMillis / 1000

                _uiState.update {
                    it.copy(remainingTimeFormatted = formatTime(remainingSeconds))
                }

                if (remainingMillis <= 0L) {
                    _uiState.update { it.copy(isResendEnabled = true, isCountingDown = false) }
                    break // 타이머 종료
                }
                kotlinx.coroutines.delay(100)
            }
        }
    }

    private fun handleCodeInput(newCode: String) {
        val digitsOnly = newCode.filter { it.isDigit() }.take(expectedCode.length)
        _uiState.update {
            it.copy(
                code = digitsOnly,
                // 코드를 입력하는 중에는 에러 상태를 즉시 해제
                isError = false
            )
        }
    }

    private fun verifyCode() {
        if (_uiState.value.code == expectedCode) {
            // 성공 시 화면 이동 이벤트 발생
            viewModelScope.launch {
                _navEvent.emit(VerifyCodeNavEvent.NavigateToNextScreen)
            }
        } else {
            // 실패 시 에러 상태 업데이트
            _uiState.update { it.copy(isError = true) }
        }
    }

    private fun formatTime(totalSeconds: Long): String {
        val m = (totalSeconds / 60).toInt()
        val s = (totalSeconds % 60).toInt()
        return "${m.toString().padStart(2, '0')}:${s.toString().padStart(2, '0')}"
    }

    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel() // ViewModel이 소멸될 때 타이머도 함께 취소
    }
}

data class VerifyCodeUiState(
    val code: String = "",
    val isError: Boolean = false,
    val remainingTimeFormatted: String = "05:00",
    val isCountingDown: Boolean = true,
    val isResendEnabled: Boolean = false // '재전송' 버튼 활성화 여부
)

/** UI -> ViewModel */
sealed interface VerifyCodeUiEvent {
    data class CodeChanged(val code: String) : VerifyCodeUiEvent
    data object NextClicked : VerifyCodeUiEvent
    data object ResendClicked : VerifyCodeUiEvent
    data object ScreenEntered : VerifyCodeUiEvent // 화면에 처음 진입했음을 알리는 이벤트
}

/** ViewModel -> UI */
sealed interface VerifyCodeNavEvent {
    data object NavigateToNextScreen : VerifyCodeNavEvent
    // data object NavigateBack : VerifyCodeNavEvent
}