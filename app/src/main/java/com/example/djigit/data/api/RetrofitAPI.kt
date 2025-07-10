package com.example.djigit.data.api

import com.example.djigit.data.model.Tokens
import com.example.djigit.data.model.urls
import com.example.djigit.features.login.LoginVariables
import retrofit2.Response
import retrofit2.http.*

interface RetrofitAPI {

    @POST(urls.loginURL)
    suspend fun login(@Body dataModel: LoginVariables): Response<Tokens>

}