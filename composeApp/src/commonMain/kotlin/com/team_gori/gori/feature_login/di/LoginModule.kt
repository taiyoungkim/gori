package com.team_gori.gori.feature_login.di

import com.team_gori.gori.feature_login.data.remote.LoginDataSource
import com.team_gori.gori.feature_login.data.repository.LoginDataRepositoryImpl
import com.team_gori.gori.feature_login.domain.repository.user.LoginDataRepository
import com.team_gori.gori.feature_login.domain.usecase.LoginUseCase
import org.koin.dsl.module

val profileModule = module {
    // Data
    single { LoginDataSource(get()) }
    single<LoginDataRepository> { LoginDataRepositoryImpl(get()) }

    // Domain
    factory { LoginUseCase(get()) } // Repository 주입

    // Presentation
    // viewModel { ProfileViewModel(get()) } // UseCase 주입 (Android)
    // factory { ProfileViewModel(get()) }   // UseCase 주입 (다른 플랫폼)
}