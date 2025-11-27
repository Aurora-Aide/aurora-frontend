package com.example.aurora.data.repository

import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.entity.ForgotPassEntity
import com.example.aurora.data.entity.UserEntity

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserEntity>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<UserEntity>
    suspend fun addDispenser(modelNumber: String, name: String): Result<DispenserEntity>
    suspend fun forgotPass(email: String): Result<Unit>
    suspend fun resetPass(password: String, reset: String): Result<ForgotPassEntity>
}