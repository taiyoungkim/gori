package com.team_gori.gori.feature_login.data.remote

import com.team_gori.gori.core.config.NetworkConfig
import com.team_gori.gori.feature_login.data.remote.dto.LoginDataDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post

class LoginDataSource(
    private val httpClient: HttpClient
) {
    private val baseUrl = NetworkConfig.BASE_URL

    suspend fun fetchLoginDto(email: String): LoginDataDto {
        return httpClient.post("$baseUrl/dev/login/$email").body()
    }
}