package com.example.aurora.domain.usecase.admin

import com.example.aurora.data.model.AdminDispenserModel
import com.example.aurora.data.repository.AuthRepository

class AdminRenameDispenserUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(id: Int, name: String): Result<AdminDispenserModel> =
        repository.adminRenameDispenser(id, name)
}

