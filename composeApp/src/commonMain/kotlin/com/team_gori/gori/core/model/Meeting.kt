package com.team_gori.gori.core.model

data class Meeting(
    val id: String,
    val dateTime: String, // 예: "내일, 오후 12시"
    val title: String, // 예: "도자기 원데이 클래스"
    val imageUrl: String, // 썸네일 이미지 URL
    val currentParticipants: Int,
    val maxParticipants: Int,
    val location: String // 예: "송파구"
) {
    val isFull: Boolean get() = currentParticipants >= maxParticipants
}