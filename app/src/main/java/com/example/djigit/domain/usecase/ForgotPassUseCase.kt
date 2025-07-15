package com.example.djigit.domain.usecase

import com.example.djigit.data.repository.AuthRepository

class ForgotPassUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email:String) =
        repository.forgotPass(email)
}