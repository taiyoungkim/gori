package com.team_gori.gori.feature_meeting.domain.usecase

import com.team_gori.gori.feature_meeting.domain.repository.MeetingRepository

class DeleteMeetingUseCase(private val repository: MeetingRepository) {
    suspend operator fun invoke(meetingId: Long): Result<Unit> {
        return repository.deleteMeeting(meetingId)
    }
}