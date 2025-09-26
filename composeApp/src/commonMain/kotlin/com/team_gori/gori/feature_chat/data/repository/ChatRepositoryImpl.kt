package com.team_gori.gori.feature_chat.data.repository

import com.team_gori.gori.feature_chat.data.datasource.ChatWebSocketDataSource
import com.team_gori.gori.feature_chat.domain.model.*
import com.team_gori.gori.feature_chat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
// dummy
class ChatRepositoryImpl(private val webSocketDataSource: ChatWebSocketDataSource) :
    ChatRepository {
    override fun observeConnectionState(): Flow<ChatConnectionState> = webSocketDataSource.observeConnectionState()
    override fun observeIncomingMessages(): Flow<ChatMessage> = webSocketDataSource.observeIncomingMessages()
    override suspend fun sendMessage(messageText: String) = webSocketDataSource.sendMessage(messageText)
    override suspend fun connect() = webSocketDataSource.connect()
    override fun disconnect() = webSocketDataSource.disconnect()
    override suspend fun loadMessageHistory(beforeMessageId: String?, limit: Int): List<ChatMessage> = emptyList()
}