package com.example.aurora.domain.usecase

import com.example.aurora.data.model.UpdateScheduleRequest
import com.example.aurora.data.repository.AuthRepository

class UpdateScheduleUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(id: Int, request: UpdateScheduleRequest) =
        repository.updateSchedule(id, request)
}

