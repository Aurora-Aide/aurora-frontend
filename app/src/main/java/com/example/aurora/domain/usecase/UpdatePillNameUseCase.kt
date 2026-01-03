package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class UpdatePillNameUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(dispenserName: String, slotNumber: Int, pillName: String) =
        repository.updatePillName(dispenserName, slotNumber, pillName)
}

