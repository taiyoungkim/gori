package com.team_gori.gori.feature_chat.domain.usecase

import com.team_gori.gori.feature_chat.domain.model.ChatConnectionState
import com.team_gori.gori.feature_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ObserveChatConnectionUseCase(private val repository: ChatRepository) {
    operator fun invoke(): Flow<ChatConnectionState> = repository.observeConnectionState()
}
// ... 다른 UseCase 클래스들 (ObserveMessages, SendMessage, Connect, Disconnect) ...