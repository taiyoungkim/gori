package com.team_gori.gori.feature_meeting.domain.model

data class MeetingInput(
    val name: String,
    val category: MeetingCategory,
    val description: String,
    val meetingTime: String,
    val region: String,
    val maxMemberCount: Int,
    val genderPreference: PreferredGender,
    val ageMin: Int,
    val ageMax: Int,
    val joinType: JoinType
)