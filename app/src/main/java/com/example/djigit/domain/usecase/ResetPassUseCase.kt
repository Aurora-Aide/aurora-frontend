package com.example.djigit.domain.usecase

import com.example.djigit.data.repository.AuthRepository

class ResetPassUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(password:String, repeat: String) =
        repository.resetPass(password, repeat)
}