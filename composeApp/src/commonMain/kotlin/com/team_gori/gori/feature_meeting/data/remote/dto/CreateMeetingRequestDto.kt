package com.team_gori.gori.feature_meeting.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateMeetingRequestDto(
    val name: String,
    val category: String,
    val description: String,
    val datetime: String,
    val region: String,
    val maxMemberCount: Int,
    val gender: String,
    val ageMin: Int,
    val ageMax: Int,
    val joinType: String
)