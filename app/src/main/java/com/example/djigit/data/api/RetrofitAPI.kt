package com.example.djigit.data.api

import com.example.djigit.data.model.Cars
import com.example.djigit.data.model.ForgotPass
import com.example.djigit.data.model.Tokens
import com.example.djigit.data.model.urls
import com.example.djigit.features.forgotPassword.ForgotPassVariables
import com.example.djigit.features.forgotPassword.ResetPassVariables
import com.example.djigit.features.login.LoginVariables
import com.example.djigit.features.signup.CarVariables
import com.example.djigit.features.signup.SignupVariables
import retrofit2.Response
import retrofit2.http.*

interface RetrofitAPI {

    @POST(urls.loginURL)
    suspend fun login(@Body dataModel: LoginVariables): Response<Tokens>


    @POST(urls.signupURL)
    suspend fun signup(@Body dataModel: SignupVariables): Response<Tokens>

    @POST(urls.addCarURL)
    suspend fun addCar(@Body dataModel: CarVariables): Response<Cars>
    //must have token doesnt now

    @POST(urls.forgotPasswordURL)
    suspend fun forgotPass(@Body dataModel: ForgotPassVariables): Response<Unit>

    @POST(urls.resetPasswordURL)
    suspend fun resetPass(@Body dataModel: ResetPassVariables): Response<ForgotPass>

}