package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class AddDispenserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(modelNumber: String, name: String, token: String) =
        repository.addDispenser(modelNumber, name, token)
}
