package com.example.aurora.data.api

import com.example.aurora.data.model.Cars
import com.example.aurora.data.model.ForgotPass
import com.example.aurora.data.model.Tokens
import com.example.aurora.data.model.urls
import com.example.aurora.features.forgotPassword.ForgotPassVariables
import com.example.aurora.features.forgotPassword.ResetPassVariables
import com.example.aurora.features.login.LoginVariables
import com.example.aurora.features.signup.CarVariables
import com.example.aurora.features.signup.SignupVariables
import retrofit2.Response
import retrofit2.http.*

interface RetrofitAPI {

    @POST(urls.loginURL)
    suspend fun login(@Body dataModel: LoginVariables): Response<Tokens>


    @POST(urls.signupURL)
    suspend fun signup(@Body dataModel: SignupVariables): Response<Tokens>

    @POST(urls.addCarURL)
    suspend fun addCar(@Body dataModel: CarVariables): Response<Cars>
    //must have token doesn't now

    @POST(urls.forgotPasswordURL)
    suspend fun forgotPass(@Body dataModel: ForgotPassVariables): Response<Unit>

    @POST(urls.resetPasswordURL)
    suspend fun resetPass(@Body dataModel: ResetPassVariables): Response<ForgotPass>

}