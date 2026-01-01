package com.example.aurora.domain.usecase

import com.example.aurora.data.entity.DispensersEntity
import com.example.aurora.data.repository.AuthRepository

class ListAllUserDispensersUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<DispensersEntity> =
        repository.listAllUserDispensers()
}