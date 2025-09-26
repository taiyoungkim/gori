package com.team_gori.gori.feature_login.domain.model.user


data class LoginData(
    val accessToken: String,
    val isSignedUpCompleted: Boolean,
)
