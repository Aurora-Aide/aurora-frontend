package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class DispenserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(modelNumber: String, name: String) =
        repository.addDispenser(modelNumber, name)
}
