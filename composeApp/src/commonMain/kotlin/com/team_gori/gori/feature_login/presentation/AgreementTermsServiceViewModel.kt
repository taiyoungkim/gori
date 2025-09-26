package com.team_gori.gori.feature_login.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AgreementTermsServiceViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AgreementUiState())
    val uiState = _uiState.asStateFlow()

    fun onAllAgreedChange(isChecked: Boolean) {
        _uiState.update {
            it.copy(
                allAgreed = isChecked,
                termsAgreed = isChecked,
                privacyAgreed = isChecked,
                marketingAgreed = isChecked
            )
        }
    }

    fun onTermsAgreedChange(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                termsAgreed = isChecked,
                allAgreed = isChecked && currentState.privacyAgreed && currentState.marketingAgreed
            )
        }
    }

    fun onPrivacyAgreedChange(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                privacyAgreed = isChecked,
                allAgreed = currentState.termsAgreed && isChecked && currentState.marketingAgreed
            )
        }
    }

    fun onMarketingAgreedChange(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                marketingAgreed = isChecked,
                allAgreed = currentState.termsAgreed && currentState.privacyAgreed && isChecked
            )
        }
    }
}

data class AgreementUiState(
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