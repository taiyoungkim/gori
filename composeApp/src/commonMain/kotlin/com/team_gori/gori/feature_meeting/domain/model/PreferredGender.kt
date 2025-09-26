package com.team_gori.gori.feature_meeting.domain.model

enum class PreferredGender(val displayName: String) {
    ANY("누구나"),
    MALE("남자만"),
    FEMALE("여자만");

    companion object {
        fun fromApiValue(apiValue: String): PreferredGender? {
            return values().find { it.name.equals(apiValue, ignoreCase = true) }
        }
    }
}