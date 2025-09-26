package com.team_gori.gori.feature_meeting.domain.model

enum class MeetingCategory(val displayName: String) {
    HEALTH("건강/운동"),
    FOOD("맛집/까페"),
    TRAVEL("나들이/여행"),
    FINANCE("재테크"),
    HOBBY("취미"),
    OTHER("기타");

    companion object {
        fun fromDisplayName(displayName: String): MeetingCategory? {
            return entries.find { it.displayName == displayName }
        }

        fun fromApiValue(apiValue: String): MeetingCategory? {
            return entries.find { it.name.equals(apiValue, ignoreCase = true) }
        }
    }
}
