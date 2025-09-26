package com.team_gori.gori.feature_login.domain.repository.user

import com.team_gori.gori.feature_login.domain.model.user.LoginData

interface LoginDataRepository {
    suspend fun getLoginData(email: String): Result<LoginData>
}