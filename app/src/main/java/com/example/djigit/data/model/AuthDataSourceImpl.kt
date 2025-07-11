package com.example.djigit.data.model

import com.example.djigit.data.api.RetrofitAPI
import com.example.djigit.data.requestBody
import com.example.djigit.data.sorce.AuthDataSource
import com.example.djigit.features.login.LoginVariables
import com.example.djigit.features.signup.CarVariables
import com.example.djigit.features.signup.SignupVariables

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
}