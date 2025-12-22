package com.example.aurora.data.api

import com.example.aurora.data.model.Dispensers
import com.example.aurora.data.model.ForgotPass
import com.example.aurora.data.model.Logout
import com.example.aurora.data.model.Tokens
import com.example.aurora.data.model.urls
import com.example.aurora.features.forgotPassword.ForgotPassVariables
import com.example.aurora.features.forgotPassword.ResetPassVariables
import com.example.aurora.features.login.LoginVariables
import com.example.aurora.features.profile.LogoutVariables
import com.example.aurora.features.signup.DispenserVariables
import com.example.aurora.features.signup.SignupVariables
import retrofit2.Response
import retrofit2.http.*

interface RetrofitAPI {

    // authentication

    @POST(urls.loginURL)
    suspend fun login(@Body dataModel: LoginVariables): Response<Tokens>

    @POST(urls.signupURL)
    suspend fun signup(@Body dataModel: SignupVariables): Response<Tokens>

    @POST(urls.registerDispenserURL)
    suspend fun registerDispenser(@Body dataModel: DispenserVariables): Response<Dispensers>
    //must have token doesnt now

    @POST(urls.forgotPasswordURL)
    suspend fun forgotPass(@Body dataModel: ForgotPassVariables): Response<Unit>

    @POST(urls.resetPasswordURL)
    suspend fun resetPass(@Body dataModel: ResetPassVariables): Response<ForgotPass>

    @POST(urls.logoutURL)
    suspend fun logout(@Body dataModel: LogoutVariables): Response<Logout>


    //TODO
    @POST(urls.logoutURL)
    suspend fun deleteAccount(@Body dataModel: LogoutVariables): Response<Logout>

    // dispensers

    
}