package com.team_gori.gori.feature_chat.domain.usecase

import com.team_gori.gori.feature_chat.domain.repository.ChatRepository

class ConnectChatUseCase(private val repository: ChatRepository) {
    suspend operator fun invoke() {
        repository.connect()
    }
}