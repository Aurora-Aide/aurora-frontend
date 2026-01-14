package com.example.aurora.domain.usecase

import com.example.aurora.data.entity.UserEntity
import com.example.aurora.data.repository.AuthRepository

class GetUserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<UserEntity> =
        repository.getUser()
}