package com.team_gori.gori.feature_login.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDataDto(
    @SerialName("accessToken")
    val accessToken: String,

    @SerialName("isSignedUpCompleted")
    val isSignedUpCompleted: Boolean,
)