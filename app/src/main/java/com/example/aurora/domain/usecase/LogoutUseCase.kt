package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke() =
        repository.logout()
}