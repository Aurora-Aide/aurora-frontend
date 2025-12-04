package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String, password: String) =
        repository.login(email, password)
}