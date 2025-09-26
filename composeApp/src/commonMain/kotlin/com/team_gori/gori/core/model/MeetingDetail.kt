package com.team_gori.gori.core.model

data class MeetingDetail(
    val id: String,
    val title: String,
    val categoryLabel: String,       // 예: "건강/운동"
    val isFull: Boolean,
    val imageUrl: String,            // 대표 이미지
    val dateTime: String,            // 예: "2025년 2월 26일 일요일 오후 2시"
    val region: String,              // 예: "서울 구로구"
    val target: String,              // 예: "여자만 60세 ~ 65세"
    val capacity: String,            // 예: "정원 20명"
    val description: String,         // 본문 (마크업 없이 단순 텍스트)
    val feeText: String? = null,
    val host: MemberUi,
    val members: List<MemberUi>,         // 일부만(예: 상위 8명)
    val currentCount: Int,
    val maxCount: Int,
    val waitingCount: Int,               // 대기 인원 수
)

data class MemberUi(
    val id: String,
    val name: String,
    val age: Int?,
    val gender: Gender?,
    val avatarUrl: String?
)
