package com.team_gori.gori

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform