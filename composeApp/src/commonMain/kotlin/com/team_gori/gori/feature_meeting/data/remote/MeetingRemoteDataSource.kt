package com.team_gori.gori.feature_meeting.data.remote

import com.team_gori.gori.core.config.NetworkConfig
import com.team_gori.gori.feature_meeting.data.remote.dto.CreateMeetingRequestDto
import com.team_gori.gori.feature_meeting.data.remote.dto.CreateMeetingResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*

class MeetingRemoteDataSource(private val httpClient: HttpClient) {
    // 모임 생성 API 호출
    suspend fun createMeeting(request: CreateMeetingRequestDto): CreateMeetingResponseDto {
        try {
            return httpClient.post("${NetworkConfig.BASE_URL}/meeting") {
                contentType(ContentType.Application.Json)
                // 필요시 header("Authorization", "Bearer ...") 추가
                setBody(request)
            }.body()
        } catch (e: Exception) {
            println("API Error: createMeeting failed - ${e.message}")
            throw e
        }
    }

    // 지역 목록 조회 API 호출
    suspend fun getRegions(): List<String> {
        try {
            return httpClient.get("${NetworkConfig.BASE_URL}/region").body<List<String>>()
        } catch (e: Exception) {
            println("API Error: getRegions failed - ${e.message}")
            throw e
        }
    }

    // 모임 삭제 API 호출
    suspend fun deleteMeeting(meetingId: Long) { // 성공 시 반환값 없음
        try {
            val response: HttpResponse = httpClient.delete("${NetworkConfig.BASE_URL}/meeting/$meetingId") {
            }

            // Ktor 는 기본적으로 2xx 외 상태코드에서 예외 발생시킴
            // 따라서 별도 상태 코드 체크 없이, 예외가 발생하지 않으면 성공으로 간주 가능
            // 만약 non-2xx 에서도 예외가 발생하지 않도록 설정했다면 여기서 response.status 확인 필요
            if (!response.status.isSuccess()) {
                // 필요하다면 여기서 특정 예외를 던지거나 로깅 처리
                println("API Error: deleteMeeting failed with status ${response.status}")
                // 기본 설정에서는 Ktor가 ClientRequestException 등을 던지므로 이 블록은 필요 없을 수 있음
                throw Exception("Failed to delete meeting: ${response.status}")
            }
            // 성공 시 (2xx) 특별히 반환할 내용 없음

        } catch (e: Exception) {
            println("API Error: deleteMeeting failed - ${e.message}")
            throw e
        }
    }
}