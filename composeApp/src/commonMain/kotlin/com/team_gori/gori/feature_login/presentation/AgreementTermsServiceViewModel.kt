package com.team_gori.gori.feature_login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AgreementTermsServiceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AgreementTermsServiceUiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<AgreementTermsServiceNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    fun onEvent(event: AgreementTermsServiceUiEvent) {
        when (event) {
            is AgreementTermsServiceUiEvent.AllAgreementToggled -> updateAllAgreements(event.isChecked)
            is AgreementTermsServiceUiEvent.TermsAgreementToggled -> updateTermsAgreement(event.isChecked)
            is AgreementTermsServiceUiEvent.PrivacyAgreementToggled -> updatePrivacyAgreement(event.isChecked)
            is AgreementTermsServiceUiEvent.MarketingAgreementToggled -> updateMarketingAgreement(event.isChecked)
            AgreementTermsServiceUiEvent.NextClicked -> navigateToSignUp()
        }
    }

    private fun updateAllAgreements(isChecked: Boolean) {
        _uiState.update {
            it.copy(
                allAgreed = isChecked,
                termsAgreed = isChecked,
                privacyAgreed = isChecked,
                marketingAgreed = isChecked,
            )
        }
    }

    private fun updateTermsAgreement(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                termsAgreed = isChecked,
                allAgreed = isChecked && currentState.privacyAgreed && currentState.marketingAgreed,
            )
        }
    }

    private fun updatePrivacyAgreement(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                privacyAgreed = isChecked,
                allAgreed = currentState.termsAgreed && isChecked && currentState.marketingAgreed,
            )
        }
    }

    private fun updateMarketingAgreement(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                marketingAgreed = isChecked,
                allAgreed = currentState.termsAgreed && currentState.privacyAgreed && isChecked,
            )
        }
    }

    private fun navigateToSignUp() {
        if (!_uiState.value.isNextButtonEnabled) return

        viewModelScope.launch {
            _navEvent.emit(AgreementTermsServiceNavEvent.NavigateToSignUp)
        }
    }
}

data class AgreementTermsServiceUiState(
    val allAgreed: Boolean = false,
    val termsAgreed: Boolean = false, // [필수] 이용약관
    val privacyAgreed: Boolean = false, // [필수] 개인정보
    val marketingAgreed: Boolean = false, // [선택] 마케팅
) {
    /**
     * '전체 동의' 체크박스의 상태를 결정합니다.
     * 선택 약관(마케팅)을 포함한 모든 약관이 동의되었는지 확인합니다.
     */
    val isAllAgreedChecked: Boolean
        get() = termsAgreed && privacyAgreed && marketingAgreed

    /**
     * '다음' 버튼의 활성화 상태를 결정합니다.
     * '필수' 약관에만 모두 동의했는지 확인합니다.
     */
    val isNextButtonEnabled: Boolean
        get() = termsAgreed && privacyAgreed
}

sealed interface AgreementTermsServiceUiEvent {
    data class AllAgreementToggled(val isChecked: Boolean) : AgreementTermsServiceUiEvent
    data class TermsAgreementToggled(val isChecked: Boolean) : AgreementTermsServiceUiEvent
    data class PrivacyAgreementToggled(val isChecked: Boolean) : AgreementTermsServiceUiEvent
    data class MarketingAgreementToggled(val isChecked: Boolean) : AgreementTermsServiceUiEvent
    data object NextClicked : AgreementTermsServiceUiEvent
}

sealed interface AgreementTermsServiceNavEvent {
    data object NavigateToSignUp : AgreementTermsServiceNavEvent
}
