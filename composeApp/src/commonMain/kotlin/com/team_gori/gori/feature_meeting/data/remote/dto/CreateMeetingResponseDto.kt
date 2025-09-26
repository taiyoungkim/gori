package com.team_gori.gori.feature_meeting.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateMeetingResponseDto(
    val meetingId: Long
)