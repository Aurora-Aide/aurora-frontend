package com.example.aurora.data.model

import com.example.aurora.data.api.RetrofitAPI
import com.example.aurora.data.requestBody
import com.example.aurora.data.sorce.AuthDataSource
import com.example.aurora.features.forgotPassword.ForgotPassVariables
import com.example.aurora.features.forgotPassword.ResetPassVariables
import com.example.aurora.features.login.LoginVariables
import com.example.aurora.features.signup.CarVariables
import com.example.aurora.features.signup.SignupVariables

class AuthDataSourceImpl(private val retrofit: RetrofitAPI): AuthDataSource {
    override suspend fun login(email: String, password: String): Result<Tokens> {
        return requestBody(retrofit.login(LoginVariables(email, password)))
    }

    override suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<Tokens> {
        return requestBody(retrofit.signup(SignupVariables(email, password, firstName, lastName)))
    }

    override suspend fun addCar(brand: String, model: String, licensePlate: String): Result<Cars> {
        return requestBody(retrofit.addCar(CarVariables(brand, model, licensePlate)))
    }

    override suspend fun forgotPass(email: String): Result<Unit> {
        return requestBody(retrofit.forgotPass(ForgotPassVariables(email)))
    }

    override suspend fun resetPass(password: String, token: String): Result<ForgotPass> {
        return requestBody(retrofit.resetPass(ResetPassVariables(password, token)))
    }
}