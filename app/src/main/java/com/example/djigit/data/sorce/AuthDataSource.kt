package com.example.djigit.data.sorce

import com.example.djigit.data.model.Tokens

interface AuthDataSource {
    suspend fun login(email: String, password: String): Result<Tokens>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<Tokens>
}