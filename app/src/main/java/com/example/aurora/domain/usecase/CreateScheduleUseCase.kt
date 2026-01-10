package com.example.aurora.domain.usecase

import com.example.aurora.data.model.CreateScheduleRequest
import com.example.aurora.data.repository.AuthRepository

class CreateScheduleUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(containerId: Int, request: CreateScheduleRequest) =
        repository.createSchedule(containerId, request)
}

