package com.team_gori.gori

import android.app.Application
import com.team_gori.gori.di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}