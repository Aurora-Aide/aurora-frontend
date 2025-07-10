package com.example.djigit.data.repository

import com.example.djigit.data.entity.UserEntity

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserEntity>
    suspend fun signup(email: String, password: String, firstName: String, lastname: String): Result<UserEntity>
}