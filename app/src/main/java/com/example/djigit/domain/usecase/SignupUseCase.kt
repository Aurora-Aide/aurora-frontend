package com.example.djigit.domain.usecase

import com.example.djigit.data.repository.AuthRepository

class SignupUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String, password: String, firstName: String, lastName: String) =
        repository.signup(email, password, firstName, lastName)
}