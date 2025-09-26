package com.team_gori.gori.feature_login.presentation.sign_up

/**
 * UI(Screen) -> ViewModel 로 전달되는 이벤트
 */
sealed interface SignUpUiEvent {
    data class NameChanged(val name: String) : SignUpUiEvent
    data class BirthDateChanged(val date: String) : SignUpUiEvent
    data class BackChanged(val back: String) : SignUpUiEvent
    data class PhoneNumberChanged(val number: String) : SignUpUiEvent
    data class CarrierSelected(val carrier: String) : SignUpUiEvent
    data object NextClicked : SignUpUiEvent
    data object BackClicked : SignUpUiEvent
}

/**
 * ViewModel -> UI(Screen) 로 전달되는 일회성 탐색 이벤트
 */
sealed interface SignUpNavEvent {
    data object GoBack : SignUpNavEvent
    data object NavigateToIneligible : SignUpNavEvent
    data object NavigateToBlackList : SignUpNavEvent
    data object NavigateToVerifyCode : SignUpNavEvent
    // data object SignUpSuccess : SignUpNavEvent // (확장) 성공 시
}