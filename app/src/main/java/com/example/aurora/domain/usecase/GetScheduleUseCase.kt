package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class GetScheduleUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(id: Int) = repository.getSchedule(id)
}

