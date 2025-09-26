package com.team_gori.gori.feature_login.domain.usecase

import com.team_gori.gori.feature_login.domain.model.user.LoginData
import com.team_gori.gori.feature_login.domain.repository.user.LoginDataRepository

class LoginUseCase(
    private val repository: LoginDataRepository
) {
    suspend operator fun invoke(email: String): Result<LoginData> {
        return repository.getLoginData(email)
    }
}