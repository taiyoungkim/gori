package com.team_gori.gori.feature_login.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.team_gori.gori.util.Logger
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.periodUntil
import kotlinx.datetime.todayIn
import kotlinx.datetime.Clock

class SignUpViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<SignUpNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    fun onEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.NameChanged -> _uiState.update { it.copy(name = event.name) }
            is SignUpUiEvent.BirthDateChanged -> _uiState.update { it.copy(birthDate = event.date) }
            is SignUpUiEvent.BackChanged -> _uiState.update { it.copy(back = event.back) }
            is SignUpUiEvent.PhoneNumberChanged -> _uiState.update { it.copy(phoneNumber = event.number) }
            is SignUpUiEvent.CarrierSelected -> _uiState.update { it.copy(carrier = event.carrier) }
            is SignUpUiEvent.NextClicked -> handleNextClick()
            is SignUpUiEvent.BackClicked -> handleBackClick()
        }
    }

    private fun handleNextClick() {
        val currentState = _uiState.value
        if (!currentState.isNextButtonEnabled) return

        if (currentState.currentStep == SignUpStep.BIRTH_DATE) {
            if (!isAgeEligible(currentState.birthDate, currentState.back)) {
                viewModelScope.launch { _navEvent.emit(SignUpNavEvent.NavigateToIneligible) }
                return
            }
        } else if (_uiState.value.currentStep == SignUpStep.PHONE_NUMBER) {
//            if (isBlackList)
//                viewModelScope.launch { _navEvent.emit(SignUpNavEvent.NavigateToBlackList) }
            viewModelScope.launch { _navEvent.emit(SignUpNavEvent.NavigateToVerifyCode) }
            return
        }

        val nextStep = when (currentState.currentStep) {
            SignUpStep.NAME -> SignUpStep.BIRTH_DATE
            SignUpStep.BIRTH_DATE -> SignUpStep.PHONE_NUMBER
            SignUpStep.PHONE_NUMBER -> SignUpStep.COMPLETE
            SignUpStep.COMPLETE -> return
        }
        _uiState.update { it.copy(currentStep = nextStep) }
    }

    private fun handleBackClick() {
        if (_uiState.value.currentStep == SignUpStep.NAME) {
            viewModelScope.launch { _navEvent.emit(SignUpNavEvent.GoBack) }
            return
        }

        val prevStep = when (_uiState.value.currentStep) {
            SignUpStep.COMPLETE, SignUpStep.PHONE_NUMBER -> SignUpStep.BIRTH_DATE
            SignUpStep.BIRTH_DATE -> SignUpStep.NAME
            else -> return
        }
        _uiState.update { it.copy(currentStep = prevStep) }
    }

    fun onNextClicked() {
        handleNextClick()
//        val currentStep = _uiState.value.currentStep
//        val nextStep = when (currentStep) {
//            SignUpStep.NAME -> SignUpStep.BIRTH_DATE
//            SignUpStep.BIRTH_DATE -> SignUpStep.PHONE_NUMBER
//            SignUpStep.PHONE_NUMBER -> SignUpStep.COMPLETE
//            SignUpStep.COMPLETE -> return
//        }
//        _uiState.update { it.copy(currentStep = nextStep) }
    }

    fun onBackClicked() {
        val currentStep = _uiState.value.currentStep
        val prevStep = when (currentStep) {
            SignUpStep.COMPLETE, SignUpStep.PHONE_NUMBER -> SignUpStep.BIRTH_DATE
            SignUpStep.BIRTH_DATE -> SignUpStep.NAME
            SignUpStep.NAME -> {
                // TODO: 첫 단계이므로 회원가입 플로우를 종료하는 로직 (ex: 화면 닫기)
                return
            }
        }
        _uiState.update { it.copy(currentStep = prevStep) }
    }

    /**
     * 만 나이가 50세 이상인지 확인하는 함수
     * @param birthDateStr "YYMMDD" 형식의 8자리 생년월일 문자열
     */
    private fun isAgeEligible(birthDate6: String, back1: String): Boolean {
        // 입력값 유효성 검사
        if (birthDate6.length < 6 || back1.length < 7) return false

        return try {
            // 뒤 첫째 자리를 기준으로 1900년대생인지 2000년대생인지 결정
            val yearPrefix = when (back1.first()) {
                '1', '2' -> "19" // 1900년대 출생
                '3', '4' -> "20" // 2000년대 출생
                else -> return false // 유효하지 않은 값
            }

            // 전체 년도(YYYY) 조합
            val year = (yearPrefix + birthDate6.substring(0, 2)).toInt()
            val month = birthDate6.substring(2, 4).toInt()
            val day = birthDate6.substring(4, 6).toInt()

            val birthDate = LocalDate(year, month, day)
            Logger.d("testtest", "$birthDate")

            val today = Clock.System.todayIn(TimeZone.currentSystemDefault())

            // 만 나이 계산
            val period = birthDate.periodUntil(today)

            Logger.d("testtest", "$period")
            period.years >= 50

        } catch (e: Exception) {
            // "20990230"과 같이 존재하지 않는 날짜 형식일 경우
            false
        }
    }
}