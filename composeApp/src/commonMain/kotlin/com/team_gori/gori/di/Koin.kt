package com.team_gori.gori.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module

// 플랫폼별 모듈 및 모든 피처 모듈을 받아 Koin 시작
fun initKoin(platformSpecificModules: List<Module> = emptyList()) {
    startKoin {
         printLogger() // Koin 로그 필요시 활성화
        modules(
            getAllModules() + platformSpecificModules
        )
    }
}

// 모든 공통 모듈 리스트 반환 (피처 모듈 추가 시 여기에 포함)
private fun getAllModules(): List<Module> {
    return listOf(
        coreModule,
        chatModule,
        meetingModule,
        loginModule
        // 다른 피처 모듈 추가...
    )
}

// 각 모듈 정의는 expect로 선언 (플랫폼별 actual 구현 필요)
expect val coreModule: Module
expect val chatModule: Module
expect val meetingModule: Module
expect val loginModule: Module
// 다른 피처 모듈 expect 선언...