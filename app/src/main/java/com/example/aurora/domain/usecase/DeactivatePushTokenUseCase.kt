package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class DeactivatePushTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(token: String) = repository.deactivatePushToken(token)
}
