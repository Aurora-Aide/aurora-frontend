package com.example.djigit.data.repository

import com.example.djigit.data.entity.UserEntity

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserEntity>
}