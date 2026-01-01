package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class UpdateNamesUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(firstName: String?, lastName: String?) =
        repository.updateNames(firstName, lastName)
}

