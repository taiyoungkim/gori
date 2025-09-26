package com.team_gori.gori.core.data.network

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createHttpClient() = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL // 개발 중에는 ALL, 배포 시에는 INFO 또는 NONE
    }
    install(WebSockets) {
        // 필요시 설정 추가
    }
    // 기타 필요한 플러그인 및 설정 추가
}
