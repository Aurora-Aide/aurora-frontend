package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class ListAllUserDispensersUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(accessToken: String) =
        repository.listAllUserDispensers(accessToken)
}