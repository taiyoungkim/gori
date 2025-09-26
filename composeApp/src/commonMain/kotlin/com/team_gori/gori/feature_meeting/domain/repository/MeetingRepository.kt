package com.team_gori.gori.feature_meeting.domain.repository

import com.team_gori.gori.feature_meeting.domain.model.MeetingCreationResult
import com.team_gori.gori.feature_meeting.domain.model.MeetingInput
import com.team_gori.gori.feature_meeting.domain.model.Region

interface MeetingRepository {
    // 모임 생성
    suspend fun createMeeting(meetingDetails: MeetingInput): Result<MeetingCreationResult>
    // 지역 목록 조회
    suspend fun getRegions(): Result<List<Region>>
    // 모임 삭제
    suspend fun deleteMeeting(meetingId: Long): Result<Unit> // 성공 시 Unit 반환
}