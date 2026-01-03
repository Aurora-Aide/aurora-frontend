package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class UpdateDispenserNameUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(currentName: String, newName: String) =
        repository.updateDispenserName(currentName, newName)
}

