package com.example.aurora.domain.usecase.admin

import com.example.aurora.data.model.AdminDispenserModel
import com.example.aurora.data.repository.AuthRepository

class AdminListDispensersUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<List<AdminDispenserModel>> = repository.adminListDispensers()
}

