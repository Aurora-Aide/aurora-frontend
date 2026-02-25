package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class ResetDispenserPairingUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(dispenserId: String) = repository.resetDispenserPairing(dispenserId)
}

