package com.example.djigit.data.model

import com.example.djigit.data.api.RetrofitAPI
import com.example.djigit.data.requestBody
import com.example.djigit.data.sorce.AuthDataSource
import com.example.djigit.features.login.LoginVariables

class AuthDataSourceImpl(private val retrofit: RetrofitAPI): AuthDataSource {
    override suspend fun login(email: String, password: String): Result<Tokens> {
        return requestBody(retrofit.login(LoginVariables(email, password)))
    }
}