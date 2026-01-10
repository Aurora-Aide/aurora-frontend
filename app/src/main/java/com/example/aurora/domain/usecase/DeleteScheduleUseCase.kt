package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class DeleteScheduleUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(id: Int) = repository.deleteSchedule(id)
}

