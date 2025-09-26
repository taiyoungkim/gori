package com.team_gori.gori.feature_chat.data.datasource

import com.team_gori.gori.feature_chat.domain.model.*
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.*
// Dummy
class ChatWebSocketDataSource(private val httpClient: HttpClient) {
    fun observeConnectionState(): Flow<ChatConnectionState> = flowOf(ChatConnectionState.Disconnected)
    fun observeIncomingMessages(): Flow<ChatMessage> = flowOf()
    suspend fun sendMessage(messageText: String) { println("Dummy send: $messageText") }
    suspend fun connect() { println("Dummy connect") }
    fun disconnect() { println("Dummy disconnect") }
}