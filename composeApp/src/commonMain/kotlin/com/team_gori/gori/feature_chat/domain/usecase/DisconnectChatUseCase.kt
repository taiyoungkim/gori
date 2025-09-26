package com.team_gori.gori.feature_chat.domain.usecase

import com.team_gori.gori.feature_chat.domain.repository.ChatRepository

class DisconnectChatUseCase(private val repository: ChatRepository) {
    operator fun invoke() {
        repository.disconnect()
    }
}