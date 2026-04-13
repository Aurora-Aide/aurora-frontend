package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class RegisterPushTokenUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(token: String) = repository.registerPushToken(token)
}
