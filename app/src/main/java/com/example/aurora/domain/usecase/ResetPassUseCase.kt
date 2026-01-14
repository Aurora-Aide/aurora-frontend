package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class ResetPassUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(password:String, token: String) =
        repository.resetPass(password)
}