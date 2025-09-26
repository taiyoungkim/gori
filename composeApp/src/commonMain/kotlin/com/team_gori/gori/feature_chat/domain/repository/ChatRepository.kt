package com.team_gori.gori.feature_chat.domain.repository

import com.team_gori.gori.feature_chat.domain.model.*
import kotlinx.coroutines.flow.Flow
interface ChatRepository {
    fun observeConnectionState(): Flow<ChatConnectionState>
    fun observeIncomingMessages(): Flow<ChatMessage>
    suspend fun sendMessage(messageText: String)
    suspend fun connect()
    fun disconnect()
    suspend fun loadMessageHistory(beforeMessageId: String?, limit: Int): List<ChatMessage>
}