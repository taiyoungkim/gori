package com.team_gori.gori.feature_chat.domain.usecase

import com.team_gori.gori.feature_chat.domain.repository.ChatRepository

class SendMessageUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke(messageText: String) {
        // 간단히 repository 호출. 필요시 추가 로직 (유효성 검사 등) 가능
        if (messageText.isNotBlank()) { // 예: 빈 메시지 방지
            repository.sendMessage(messageText)
        }
    }
}