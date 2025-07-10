package com.example.djigit.data.sorce

import com.example.djigit.data.model.Tokens

interface AuthDataSource {
    suspend fun login(email: String, password: String): Result<Tokens>
}