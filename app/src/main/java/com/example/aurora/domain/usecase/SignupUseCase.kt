package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository
import com.example.aurora.features.login.LoginPasswordErrors

class SignupUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) =
        repository.signup(email, password)
}