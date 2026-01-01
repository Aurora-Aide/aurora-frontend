package com.example.aurora.data.repository

import com.example.aurora.data.entity.DeleteUserEntity
import com.example.aurora.data.entity.DeleteDispenserEntity
import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.entity.DispensersEntity
import com.example.aurora.data.entity.ForgotPassEntity
import com.example.aurora.data.entity.LogoutEntity
import com.example.aurora.data.entity.UserEntity

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserEntity>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<UserEntity>
    suspend fun addDispenser(modelNumber: String, name: String, token: String): Result<DispenserEntity>
    suspend fun forgotPass(email: String): Result<Unit>
    suspend fun resetPass(password: String): Result<ForgotPassEntity>
    suspend fun logout(): Result<LogoutEntity>
    suspend fun listAllUserDispensers(): Result<DispensersEntity>
    suspend fun getUser(): Result<UserEntity>
    suspend fun deleteUser(email: String): Result<DeleteUserEntity>
    suspend fun updateNames(firstName: String?, lastName: String?): Result<UserEntity>
    suspend fun deleteDispenser(name: String): Result<DeleteDispenserEntity>
}