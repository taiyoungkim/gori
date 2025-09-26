package com.team_gori.gori.feature_meeting.domain.usecase

import com.team_gori.gori.feature_meeting.domain.model.MeetingCreationResult
import com.team_gori.gori.feature_meeting.domain.model.MeetingInput
import com.team_gori.gori.feature_meeting.domain.repository.MeetingRepository

class CreateMeetingUseCase(private val repository: MeetingRepository) {
    suspend operator fun invoke(meetingDetails: MeetingInput): Result<MeetingCreationResult> {
        return repository.createMeeting(meetingDetails)
    }
}