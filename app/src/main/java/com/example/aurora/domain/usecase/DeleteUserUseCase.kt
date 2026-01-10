package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class DeleteUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String) = repository.deleteUser(email)
}

