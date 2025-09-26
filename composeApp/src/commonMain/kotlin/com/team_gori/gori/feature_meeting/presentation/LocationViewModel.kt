package com.team_gori.gori.feature_meeting.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LocationViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LocationUiState())
    val uiState = _uiState.asStateFlow()

    private val _navEvent = MutableSharedFlow<LocationNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    fun onEvent(event: LocationUiEvent) {
        when (event) {
            is LocationUiEvent.DistrictSelected -> {
                _uiState.update { it.copy(selectedDistrict = event.district) }
            }
            is LocationUiEvent.ConfirmClicked -> {
                _uiState.value.selectedDistrict?.let {
                    viewModelScope.launch {
                        _navEvent.emit(LocationNavEvent.GoBackWithResult(it))
                    }
                }
            }
            is LocationUiEvent.ScreenEntered -> {
                loadDistricts()
            }
        }
    }

    private fun loadDistricts() {
        // 실제 앱에서는 서버나 로컬 DB에서 데이터를 가져옵니다.
        val seoulDistricts = listOf(
            "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구",
            "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구",
            "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"
        )
        _uiState.update {
            it.copy(
                districts = seoulDistricts,
                isLoading = false
            )
        }
    }
}

data class LocationUiState(
    val sido: String = "서울",
    val districts: List<String> = emptyList(),
    val selectedDistrict: String? = null,
    val isLoading: Boolean = true
) {
    val isConfirmEnabled: Boolean get() = selectedDistrict != null
}

sealed interface LocationUiEvent {
    data class DistrictSelected(val district: String) : LocationUiEvent
    data object ConfirmClicked : LocationUiEvent
    data object ScreenEntered : LocationUiEvent
}

sealed interface LocationNavEvent {
    data class GoBackWithResult(val selectedLocation: String) : LocationNavEvent
}
