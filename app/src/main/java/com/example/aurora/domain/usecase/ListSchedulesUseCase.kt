package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class ListSchedulesUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(containerId: Int) = repository.listSchedules(containerId)
}

