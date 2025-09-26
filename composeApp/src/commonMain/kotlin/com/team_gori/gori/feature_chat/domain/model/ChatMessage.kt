package com.team_gori.gori.feature_chat.domain.model

data class ChatMessage (
    val id: String,
    val senderId: String,
    val senderNickname: String,
    val text: String,
    val timestamp: Long,
    val isSentByMe: Boolean,
    val isFirstInSequence: Boolean,
)