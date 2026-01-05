package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class GetDispenserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(id: String) = repository.getDispenser(id)
}

