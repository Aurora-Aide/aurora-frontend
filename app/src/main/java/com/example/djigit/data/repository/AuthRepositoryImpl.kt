package com.example.djigit.data.repository

import com.example.djigit.data.entity.CarEntity
import com.example.djigit.data.entity.ForgotPassEntity
import com.example.djigit.data.entity.UserEntity
import com.example.djigit.data.mapper.toCarMapper
import com.example.djigit.data.mapper.toForgotPassMapper
import com.example.djigit.data.mapper.toUserEntity
import com.example.djigit.data.sorce.AuthDataSource

class AuthRepositoryImpl(private val data: AuthDataSource): AuthRepository {

    override suspend fun login(email: String, password: String): Result<UserEntity> {
        return data.login(email, password).fold(
            onSuccess = {
                token -> Result.success(token.user.toUserEntity())
            },
            onFailure = {
                error -> Result.failure(error)
            }
        )
    }

    override suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<UserEntity> {
        return data.signup(email, password, firstName, lastName).fold(
            onSuccess = {
                    token -> Result.success(token.user.toUserEntity())
            },
            onFailure = {
                    error -> Result.failure(error)
            }
        )
    }

    override suspend fun addCar(brand: String, model: String, licensePlate: String): Result<CarEntity> {
        return data.addCar(brand, model, licensePlate).fold(
            onSuccess = {
                car -> Result.success(car.toCarMapper())
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun forgotPass(email: String): Result<Unit> {
        return data.forgotPass(email).fold(
            onSuccess = {
                    msg -> Result.success(Unit)
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun resetPass(password: String, token: String): Result<ForgotPassEntity> {
        return data.resetPass(password, token).fold(
            onSuccess = {
                    msg -> Result.success(msg.toForgotPassMapper())
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}