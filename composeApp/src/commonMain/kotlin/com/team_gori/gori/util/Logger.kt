package com.team_gori.gori.util

expect object Logger {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun e(tag: String, message: String, throwable: Throwable? = null)
}