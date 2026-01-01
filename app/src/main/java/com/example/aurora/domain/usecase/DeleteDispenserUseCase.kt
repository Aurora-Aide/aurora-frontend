package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class DeleteDispenserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String) = repository.deleteDispenser(name)
}

