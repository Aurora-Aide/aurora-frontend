package com.example.aurora.data.model

import com.example.aurora.data.api.RetrofitAPI
import com.example.aurora.data.requestBody
import com.example.aurora.data.sorce.AuthDataSource
import com.example.aurora.features.forgotPassword.ForgotPassVariables
import com.example.aurora.features.forgotPassword.ResetPassVariables
import com.example.aurora.features.home.DispenserVariables
import com.example.aurora.features.login.LoginVariables
import com.example.aurora.features.profile.LogoutVariables
import com.example.aurora.features.signup.SignupVariables

class AuthDataSourceImpl(private val retrofit: RetrofitAPI): AuthDataSource {
    override suspend fun login(email: String, password: String): Result<Tokens> {
        return requestBody(retrofit.login(LoginVariables(email, password)))
    }

    override suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<Tokens> {
        return requestBody(retrofit.signup(SignupVariables(email, password, firstName, lastName)))
    }


    override suspend fun forgotPass(email: String): Result<Unit> {
        return requestBody(retrofit.forgotPass(ForgotPassVariables(email)))
    }

    override suspend fun resetPass(password: String, token: String): Result<ForgotPass> {
        return requestBody(retrofit.resetPass(ResetPassVariables(password, token)))
    }

    override suspend fun logout(refreshToken: String): Result<Logout> {
        return requestBody(retrofit.logout(LogoutVariables(refreshToken))) //token
    }

    override suspend fun listAllUserDispensers(accessToken: String): Result<Dispensers> {
        return requestBody(retrofit.listAllUserDispensers(LogoutVariables(accessToken)))  //token
    }


    override suspend fun addDispenser(modelNumber: String, name: String, accessToken: String): Result<Dispensers> {
        return requestBody(retrofit.registerDispenser(DispenserVariables(modelNumber, name, accessToken)))
    }
}