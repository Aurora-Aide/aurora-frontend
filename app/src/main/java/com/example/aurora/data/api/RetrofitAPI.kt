package com.example.aurora.data.api

import com.example.aurora.data.model.AccessToken
import com.example.aurora.data.model.Dispenser
import com.example.aurora.data.model.Dispensers
import com.example.aurora.data.model.ForgotPass
import com.example.aurora.data.model.Logout
import com.example.aurora.data.model.Refresh
import com.example.aurora.data.model.Tokens
import com.example.aurora.data.model.DeleteUserRequest
import com.example.aurora.data.model.DeleteUserResponse
import com.example.aurora.data.model.UserModel
import com.example.aurora.data.model.urls
import com.example.aurora.features.forgotPassword.ForgotPassVariables
import com.example.aurora.features.forgotPassword.ResetPassVariables
import com.example.aurora.features.home.DispenserVariables
import com.example.aurora.features.login.LoginVariables
import com.example.aurora.features.signup.SignupVariables
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST

interface RetrofitAPI {

    // authentication

    @POST(urls.loginURL)
    suspend fun login(@Body dataModel: LoginVariables): Response<Tokens>

    @POST(urls.signupURL)
    suspend fun signup(@Body dataModel: SignupVariables): Response<Tokens>

    @POST(urls.registerDispenserURL)
    suspend fun registerDispenser(@Body dataModel: DispenserVariables): Response<Dispenser>

    @POST(urls.forgotPasswordURL)
    suspend fun forgotPass(@Body dataModel: ForgotPassVariables): Response<Unit>

    @POST(urls.resetPasswordURL)
    suspend fun resetPass(@Body dataModel: ResetPassVariables): Response<ForgotPass>

    @HTTP(method = "DELETE", path = urls.deleteUserURL, hasBody = true)
    suspend fun deleteUser(@Body dataModel: DeleteUserRequest): Response<DeleteUserResponse>

    // dispensers
    @GET(urls.listAllUserDispensersURL)
    suspend fun listAllUserDispensers(): Response<List<Dispenser>>

    // profile
    @POST(urls.logoutURL)
    suspend fun logout(@Body refresh: Refresh): Response<Logout>

    @GET(urls.userURL)
    suspend fun getUser(): Response<UserModel>

    @POST(urls.refreshURL)
    suspend fun refreshToken(@Body refresh: Refresh): Response<AccessToken>
}