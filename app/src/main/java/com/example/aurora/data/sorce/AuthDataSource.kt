package com.example.aurora.data.sorce

import com.example.aurora.data.model.Cars
import com.example.aurora.data.model.ForgotPass
import com.example.aurora.data.model.Tokens

interface AuthDataSource {
    suspend fun login(email: String, password: String): Result<Tokens>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<Tokens>
    suspend fun addCar(brand: String, model: String, licensePlate: String): Result<Cars>
    suspend fun forgotPass(email: String): Result<Unit>
    suspend fun resetPass(password: String, token: String): Result<ForgotPass>
}