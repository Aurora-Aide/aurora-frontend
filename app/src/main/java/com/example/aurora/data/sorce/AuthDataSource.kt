package com.example.aurora.data.sorce

import com.example.aurora.data.model.Dispensers
import com.example.aurora.data.model.ForgotPass
import com.example.aurora.data.model.Logout
import com.example.aurora.data.model.Tokens

interface AuthDataSource {
    suspend fun login(email: String, password: String): Result<Tokens>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<Tokens>
    suspend fun addDispenser(modelNumber: String, name: String, accessToken: String): Result<Dispensers>
    suspend fun forgotPass(email: String): Result<Unit>
    suspend fun resetPass(password: String, token: String): Result<ForgotPass>
    suspend fun logout(refreshToken: String): Result<Logout>
    suspend fun listAllUserDispensers(accessToken: String): Result<Dispensers>
}