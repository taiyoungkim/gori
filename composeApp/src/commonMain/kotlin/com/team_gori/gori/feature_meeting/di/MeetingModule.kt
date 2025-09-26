package com.team_gori.gori.feature_meeting.di

import com.team_gori.gori.feature_meeting.data.remote.MeetingRemoteDataSource
import com.team_gori.gori.feature_meeting.data.repository.MeetingRepositoryImpl
import com.team_gori.gori.feature_meeting.domain.repository.MeetingRepository
import com.team_gori.gori.feature_meeting.domain.usecase.CreateMeetingUseCase
import com.team_gori.gori.feature_meeting.domain.usecase.DeleteMeetingUseCase
import com.team_gori.gori.feature_meeting.domain.usecase.GetRegionsUseCase
import org.koin.dsl.module

val meetingModuleDefinition = module {
    // Data Layer
    single { MeetingRemoteDataSource(get()) }
    single<MeetingRepository> { MeetingRepositoryImpl(get()) }

    // Domain Layer
    factory { CreateMeetingUseCase(get()) }
    factory { GetRegionsUseCase(get()) }
    factory { DeleteMeetingUseCase(get()) }

    // Presentation Layer
    // viewModel { CreateMeetingViewModel(get()) }
    // viewModel { RegionSelectViewModel(get()) }
}