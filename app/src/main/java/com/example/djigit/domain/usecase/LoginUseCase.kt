package com.example.djigit.domain.usecase

import com.example.djigit.data.repository.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String, password: String) = repository.login(email, password)
}