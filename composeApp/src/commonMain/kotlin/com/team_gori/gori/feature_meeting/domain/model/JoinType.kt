package com.team_gori.gori.feature_meeting.domain.model

enum class JoinType(val displayName: String) {
    AUTO("자동 가입"),     // 선착순 자동 가입
    APPROVAL("승인 가입"); // 방장 승인 필요

    companion object {
        fun fromApiValue(apiValue: String): JoinType? {
            return entries.find { it.name.equals(apiValue, ignoreCase = true) }
        }
    }
}