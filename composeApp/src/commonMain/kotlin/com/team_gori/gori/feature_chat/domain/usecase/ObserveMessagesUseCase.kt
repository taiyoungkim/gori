package com.team_gori.gori.feature_chat.domain.usecase

import com.team_gori.gori.feature_chat.domain.model.ChatMessage
import com.team_gori.gori.feature_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveMessagesUseCase(private val repository: ChatRepository) {
    operator fun invoke(): Flow<ChatMessage> = repository.observeIncomingMessages()
}