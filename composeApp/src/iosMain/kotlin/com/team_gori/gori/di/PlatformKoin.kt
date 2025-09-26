package com.team_gori.gori.di

import com.team_gori.gori.core.data.network.createHttpClient
import com.team_gori.gori.feature_chat.data.datasource.ChatWebSocketDataSource
import com.team_gori.gori.feature_chat.data.repository.ChatRepositoryImpl
import com.team_gori.gori.feature_chat.domain.repository.ChatRepository
import com.team_gori.gori.feature_chat.domain.usecase.ConnectChatUseCase
import com.team_gori.gori.feature_chat.domain.usecase.DisconnectChatUseCase
import com.team_gori.gori.feature_chat.domain.usecase.ObserveChatConnectionUseCase
import com.team_gori.gori.feature_chat.domain.usecase.ObserveMessagesUseCase
import com.team_gori.gori.feature_chat.domain.usecase.SendMessageUseCase
import com.team_gori.gori.feature_chat.presentation.ChatViewModel
import com.team_gori.gori.feature_login.presentation.AgreementTermsServiceViewModel
import com.team_gori.gori.feature_login.presentation.InterestViewModel
import com.team_gori.gori.feature_login.presentation.sign_up.NicknameViewModel
import com.team_gori.gori.feature_login.presentation.sign_up.SignUpViewModel
import com.team_gori.gori.feature_login.presentation.sign_up.VerifyCodeViewModel
import com.team_gori.gori.feature_meeting.data.remote.MeetingRemoteDataSource
import com.team_gori.gori.feature_meeting.data.repository.MeetingRepositoryImpl
import com.team_gori.gori.feature_meeting.domain.repository.MeetingRepository
import com.team_gori.gori.feature_meeting.domain.usecase.CreateMeetingUseCase
import com.team_gori.gori.feature_meeting.domain.usecase.DeleteMeetingUseCase
import com.team_gori.gori.feature_meeting.domain.usecase.GetRegionsUseCase
import com.team_gori.gori.feature_meeting.presentation.CreateMeetingViewModel
import com.team_gori.gori.feature_meeting.presentation.LocationViewModel
import com.team_gori.gori.feature_meeting.presentation.MeetingDetailUiState
import com.team_gori.gori.feature_meeting.presentation.MeetingDetailViewModel
import com.team_gori.gori.feature_meeting.presentation.MeetingViewModel
import io.ktor.client.HttpClient
import org.koin.dsl.module

actual val coreModule = module {
    single<HttpClient> { createHttpClient() }
}

actual val chatModule = module {
    // Data Layer
    single { ChatWebSocketDataSource(get()) }
    single<ChatRepository> { ChatRepositoryImpl(get()) }

    // Domain Layer
    factory { ObserveChatConnectionUseCase(get()) }
    factory { ObserveMessagesUseCase(get()) }
    factory { SendMessageUseCase(get()) }
    factory { ConnectChatUseCase(get()) }
    factory { DisconnectChatUseCase(get()) }

    // Presentation Layer
    factory { ChatViewModel(get(), get(), get(), get(), get()) }
}

actual val meetingModule = module {
    // Data Layer
    single { MeetingRemoteDataSource(get()) }
    single<MeetingRepository> { MeetingRepositoryImpl(get()) }

    // Domain Layer
    factory { CreateMeetingUseCase(get()) }
    factory { GetRegionsUseCase(get()) }
    factory { DeleteMeetingUseCase(get()) }

    // Presentation Layer
     factory { CreateMeetingViewModel() }
    factory { LocationViewModel() }
    factory { MeetingViewModel() }
    factory { (meetingId: String) ->
        MeetingDetailViewModel(
            meetingId = meetingId,
//            repository = get<MeetingRepository>() // or get<GetMeetingDetailUseCase>()
        )
    }
    // factory { RegionSelectViewModel(get()) }
}

actual val loginModule = module {
    factory { AgreementTermsServiceViewModel() }
    factory { SignUpViewModel() }
    factory { VerifyCodeViewModel() }
    factory { NicknameViewModel() }
    factory { InterestViewModel() }
}