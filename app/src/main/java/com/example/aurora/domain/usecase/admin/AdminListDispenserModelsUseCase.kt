package com.example.aurora.domain.usecase.admin

import com.example.aurora.data.model.AdminDispenserModelModel
import com.example.aurora.data.repository.AuthRepository

class AdminListDispenserModelsUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<List<AdminDispenserModelModel>> =
        repository.adminListDispenserModels()
}

