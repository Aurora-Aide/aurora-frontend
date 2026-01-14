package com.example.aurora.domain.usecase.admin

import com.example.aurora.data.model.AdminUserModel
import com.example.aurora.data.repository.AuthRepository

class AdminListUsersUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Result<List<AdminUserModel>> = repository.adminListUsers()
}

