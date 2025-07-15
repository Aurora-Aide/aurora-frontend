package com.example.djigit.data.repository

import com.example.djigit.data.entity.CarEntity
import com.example.djigit.data.entity.ForgotPassEntity
import com.example.djigit.data.entity.UserEntity

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserEntity>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<UserEntity>
    suspend fun addCar(brand: String, model: String, licensePlate: String): Result<CarEntity>
    suspend fun forgotPass(email: String): Result<Unit>
    suspend fun resetPass(password: String, reset: String): Result<ForgotPassEntity>
}