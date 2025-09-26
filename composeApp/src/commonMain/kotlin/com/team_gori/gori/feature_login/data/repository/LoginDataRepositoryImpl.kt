package com.team_gori.gori.feature_login.data.repository

import com.team_gori.gori.feature_login.data.remote.LoginDataSource
import com.team_gori.gori.feature_login.domain.model.user.LoginData
import com.team_gori.gori.feature_login.domain.repository.user.LoginDataRepository

class LoginDataRepositoryImpl (
    private val remoteDataSource: LoginDataSource
) : LoginDataRepository {

    override suspend fun getLoginData(email: String): Result<LoginData> {
        return try {
            val dto = remoteDataSource.fetchLoginDto(email)

            val loginData = LoginData(
                accessToken = dto.accessToken,
                isSignedUpCompleted = dto.isSignedUpCompleted
            )
            Result.success(loginData)
        } catch (e: Exception) {
            println("Error fetching user profile: ${e.message}")
            Result.failure(e)
        }
    }
}