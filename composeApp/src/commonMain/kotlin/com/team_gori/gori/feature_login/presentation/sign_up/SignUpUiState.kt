package com.team_gori.gori.feature_login.presentation.sign_up

data class SignUpUiState(
    // 1. 각 단계에서 입력받을 데이터
    val name: String = "",
    val birthDate: String = "",
    val back: String = "",
    val phoneNumber: String = "",
    val carrier: String = "",

    // 2. 현재 어떤 단계를 보여줄지 결정하는 상태
    val currentStep: SignUpStep = SignUpStep.NAME,

    // 3. 각 필드의 유효성 검사 결과 (필요 시 추가)
    val isNameValid: Boolean = true,
    // ...
) {
    /**
     * 현재 단계의 입력값이 유효한지 판단하여 '다음' 버튼의 활성화 상태를 결정합니다.
     * 이 로직은 View(Composable)에서 직접 사용됩니다.
     */
    val isNextButtonEnabled: Boolean
        get() = when (currentStep) {
            SignUpStep.NAME -> name.isNotBlank()
            SignUpStep.BIRTH_DATE -> birthDate.length >= 6 && back.length >= 7
            SignUpStep.PHONE_NUMBER -> phoneNumber.length >= 10
            SignUpStep.COMPLETE -> false
        }
}
