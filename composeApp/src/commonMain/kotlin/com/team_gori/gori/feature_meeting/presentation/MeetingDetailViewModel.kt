package com.team_gori.gori.feature_meeting.presentation

import com.team_gori.gori.core.model.Gender
import com.team_gori.gori.core.model.MeetingDetail
import com.team_gori.gori.core.model.MemberUi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MeetingDetailViewModel(
    private val meetingId: String,
    private val scope: CoroutineScope = MainScope()
) {
    private val _state = MutableStateFlow(MeetingDetailUiState())
    val state = _state.asStateFlow()
    private val _effect = MutableSharedFlow<MeetingDetailEffect>()
    val effect = _effect

    fun onEvent(e: MeetingDetailEvent) {
        when (e) {
            MeetingDetailEvent.OnClickBack  -> scope.launch { _effect.emit(MeetingDetailEffect.NavigateBack) }
            MeetingDetailEvent.OnClickShare -> scope.launch { _effect.emit(MeetingDetailEffect.Share(meetingId)) }
            MeetingDetailEvent.OnClickMore  -> scope.launch { _effect.emit(MeetingDetailEffect.ShowMoreMenu(meetingId)) }
            MeetingDetailEvent.OnRetryLoad  -> refresh()
            MeetingDetailEvent.OnClickJoin -> scope.launch { _effect.emit(MeetingDetailEffect.RequestJoin) }
            MeetingDetailEvent.OnClickSeeAllMembers -> scope.launch { _effect.emit(MeetingDetailEffect.OpenAllMembers) }
            MeetingDetailEvent.OnClickSeeWaitingMembers -> scope.launch { _effect.emit(MeetingDetailEffect.OpenWaitingMembers) }
        }
    }

    init { refresh() }

    fun refresh() {
        scope.launch {
            _state.value = _state.value.copy(content = MeetingDetailUiState.Content.Loading)
            runCatching {
                // TODO 서버 연동 시 repository.getMeetingDetail(meetingId)로 교체
                delay(400)
                MeetingDetailSamples.get(meetingId)
            }.onSuccess { data ->
                _state.value = MeetingDetailUiState(
                    titleOnAppBar = data.title,
                    content = MeetingDetailUiState.Content.Success(data)
                )
            }.onFailure { t ->
                _state.value = _state.value.copy(
                    content = MeetingDetailUiState.Content.Error(t.message ?: "불러오기 실패")
                )
            }
        }
    }

    fun onBack() = scope.launch { _effect.emit(MeetingDetailEffect.NavigateBack) }
    fun clear() { scope.cancel() }
}

object MeetingDetailSamples {
    fun get(meetingId: String) = when (meetingId) {
        "1" -> MeetingDetail(
            id = "1",
            title = "토요일 아침 파크골프 원정",
            categoryLabel = "건강/운동",
            isFull = false,
            imageUrl = "https://picsum.photos/seed/parkgolf/1200/800",
            dateTime = "2025-10-18(토) 오전 09:30",
            region = "서울 노원구",
            target = "여자만 60~65세",
            capacity = "정원 20명 (현재 12명)",
            description = """
                한빛 파크골프장에서 원정 라운딩 갑니다.
                초보자도 환영! 장비 대여 가능합니다.

                • 모임장: 김00
                • 준비물: 편한 복장, 물
                • 진행: 9홀 → 휴식 → 9홀
            """.trimIndent(),
            host = MemberUi("1","시마스시즌맛", 61, Gender.Male, "host-$meetingId"),
            members = listOf(
                MemberUi("2", "도리토스치즈맛", 61, Gender.Male, "1"),
                MemberUi("3", "바질페스토", 60, Gender.Female, "2"),
                MemberUi("4", "무화과파이", 62, Gender.Male, "3"),
                MemberUi("5", "라떼중독", 63, Gender.Female, "4"),
                MemberUi("6", "골프초보", null, Gender.Male, "5"),
                MemberUi("7", "와인좋아", 61, Gender.Male, "6"),
                MemberUi("8", "공원러버", 60, Gender.Female, "7"),
                MemberUi("9", "주말산책러", 65, Gender.Male, "8")
            ),
            currentCount = 10,
            maxCount = 20,
            waitingCount = 3,
        )
        "2" -> MeetingDetail(
            id = "2",
            title = "와인 시음회 – 보르도 위크",
            categoryLabel = "취미",
            isFull = true,
            imageUrl = "https://picsum.photos/seed/wine/1200/800",
            dateTime = "2025-10-25(토) 오후 07:00",
            region = "서울 강남구",
            target = "누구나",
            capacity = "정원 10명 (현재 10명)",
            description = "소믈리에와 함께하는 보르도 와인 라인업. 간단한 치즈/샤퀴테리 제공.",
            host = MemberUi("1","시마스시즌맛", 61, Gender.Male, "host-$meetingId"),
            members = listOf(
                MemberUi("2", "도리토스치즈맛", 61, Gender.Male, "1"),
                MemberUi("3", "바질페스토", 60, Gender.Female, "2"),
                MemberUi("4", "무화과파이", 62, Gender.Male, "3"),
                MemberUi("5", "라떼중독", 63, Gender.Female, "4"),
                MemberUi("6", "골프초보", null, Gender.Male, "5"),
                MemberUi("7", "와인좋아", 61, Gender.Male, "6"),
                MemberUi("8", "공원러버", 60, Gender.Female, "7"),
                MemberUi("9", "주말산책러", 65, Gender.Male, "8")
            ),
            currentCount = 10,
            maxCount = 20,
            waitingCount = 3,
        )
        else -> MeetingDetail(
            id = meetingId,
            title = "도자기 원데이 클래스",
            categoryLabel = "취미",
            isFull = false,
            imageUrl = "https://picsum.photos/seed/ceramic/1200/800",
            dateTime = "2025-11-02(일) 오후 02:00",
            region = "서울 송파구",
            target = "누구나",
            capacity = "정원 8명 (현재 5명)",
            description = "기초 성형부터 유약까지 한 번에! 작품은 소성 후 일주일 내 픽업.",
            host = MemberUi("1","시마스시즌맛", 61, Gender.Male, "host-$meetingId"),
            members = listOf(
                MemberUi("2", "도리토스치즈맛", 61, Gender.Male, "1"),
                MemberUi("3", "바질페스토", 60, Gender.Female, "2"),
                MemberUi("4", "무화과파이", 62, Gender.Male, "3"),
                MemberUi("5", "라떼중독", 63, Gender.Female, "4"),
                MemberUi("6", "골프초보", null, Gender.Male, "5"),
                MemberUi("7", "와인좋아", 61, Gender.Male, "6"),
                MemberUi("8", "공원러버", 60, Gender.Female, "7"),
                MemberUi("9", "주말산책러", 65, Gender.Male, "8")
            ),
            currentCount = 10,
            maxCount = 20,
            waitingCount = 3,
        )
    }
}