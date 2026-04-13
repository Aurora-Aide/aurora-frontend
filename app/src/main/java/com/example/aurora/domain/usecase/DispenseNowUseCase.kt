package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class DispenseNowUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(containerId: Int) =
        repository.dispenseNow(containerId)
}
