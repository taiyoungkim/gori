package com.team_gori.gori.feature_meeting.data.repository

import com.team_gori.gori.feature_meeting.data.remote.MeetingRemoteDataSource
import com.team_gori.gori.feature_meeting.data.remote.dto.CreateMeetingRequestDto
import com.team_gori.gori.feature_meeting.domain.model.*
import com.team_gori.gori.feature_meeting.domain.repository.MeetingRepository

class MeetingRepositoryImpl(
    private val remoteDataSource: MeetingRemoteDataSource
) : MeetingRepository {

    // 모임 생성 구현
    override suspend fun createMeeting(meetingDetails: MeetingInput): Result<MeetingCreationResult> {
        return try {
            val requestDto = mapInputToRequestDto(meetingDetails)
            val responseDto = remoteDataSource.createMeeting(requestDto)
            val resultData = MeetingCreationResult(newMeetingId = responseDto.meetingId)
            Result.success(resultData)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 지역 목록 조회 구현
    override suspend fun getRegions(): Result<List<Region>> {
        return try {
            val regionNames = remoteDataSource.getRegions()
            val regions = regionNames.map { Region(it) } // String -> Region 매핑
            Result.success(regions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 모임 삭제 구현
    override suspend fun deleteMeeting(meetingId: Long): Result<Unit> {
        return try {
            remoteDataSource.deleteMeeting(meetingId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    private fun mapInputToRequestDto(input: MeetingInput): CreateMeetingRequestDto {
        val datetimeString = input.meetingTime

        val categoryString = when (input.category) {
            MeetingCategory.HEALTH -> MeetingCategory.HEALTH.displayName
            MeetingCategory.FOOD -> MeetingCategory.FOOD.displayName
            MeetingCategory.TRAVEL -> MeetingCategory.TRAVEL.displayName
            MeetingCategory.FINANCE -> MeetingCategory.FINANCE.displayName
            MeetingCategory.HOBBY -> MeetingCategory.HOBBY.displayName
            MeetingCategory.OTHER -> MeetingCategory.OTHER.displayName
        }
        val genderString = when (input.genderPreference) {
            PreferredGender.ANY -> PreferredGender.ANY.displayName
            PreferredGender.MALE -> PreferredGender.MALE.displayName
            PreferredGender.FEMALE -> PreferredGender.FEMALE.displayName
        }
        val joinTypeString = when (input.joinType) {
            JoinType.AUTO -> JoinType.AUTO.displayName
            JoinType.APPROVAL -> JoinType.APPROVAL.displayName
        }

        return CreateMeetingRequestDto(
            name = input.name,
            category = categoryString,
            description = input.description,
            datetime = datetimeString,
            region = input.region,
            maxMemberCount = input.maxMemberCount,
            gender = genderString,
            ageMin = input.ageMin,
            ageMax = input.ageMax,
            joinType = joinTypeString
        )
    }
}