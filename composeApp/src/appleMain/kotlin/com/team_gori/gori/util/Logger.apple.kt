package com.team_gori.gori.util

import platform.Foundation.NSLog

actual object Logger {
    actual fun d(tag: String, message: String) {
        NSLog("DEBUG [$tag]: $message")
    }

    actual fun i(tag: String, message: String) {
        NSLog("INFO [$tag]: $message")
    }

    actual fun e(tag: String, message: String, throwable: Throwable?) {
        NSLog("ERROR [$tag]: $message ${throwable?.message ?: ""}")
    }
}