package com.example.aurora.domain.usecase.admin

import com.example.aurora.data.model.AdminCreateDispenserModelRequest
import com.example.aurora.data.model.AdminDispenserModelModel
import com.example.aurora.data.repository.AuthRepository

class AdminCreateDispenserModelUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(request: AdminCreateDispenserModelRequest): Result<AdminDispenserModelModel> =
        repository.adminCreateDispenserModel(request)
}

