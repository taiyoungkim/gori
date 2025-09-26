package com.team_gori.gori.feature_meeting.domain.usecase

import com.team_gori.gori.feature_meeting.domain.model.Region
import com.team_gori.gori.feature_meeting.domain.repository.MeetingRepository

class GetRegionsUseCase(private val repository: MeetingRepository) {
    suspend operator fun invoke(): Result<List<Region>> {
        return repository.getRegions()
    }
}