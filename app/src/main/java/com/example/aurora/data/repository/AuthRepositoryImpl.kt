package com.example.aurora.data.repository

import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.entity.ForgotPassEntity
import com.example.aurora.data.entity.LogoutEntity
import com.example.aurora.data.entity.UserEntity
import com.example.aurora.data.mapper.toDispenserMapper
import com.example.aurora.data.mapper.toForgotPassMapper
import com.example.aurora.data.mapper.toLogoutMapper
import com.example.aurora.data.mapper.toUserEntity
import com.example.aurora.data.sorce.AuthDataSource

class AuthRepositoryImpl(private val data: AuthDataSource): AuthRepository {

    override suspend fun login(email: String, password: String): Result<UserEntity>{
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

    override suspend fun addDispenser(modelNumber: String, name: String, token: String): Result<DispenserEntity> {
        return data.addDispenser(modelNumber, name, token).fold(
            onSuccess = {
                dispenser -> Result.success(dispenser.toDispenserMapper())
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

    override suspend fun logout(refreshToken: String): Result<LogoutEntity> {
        return data.logout(refreshToken).fold(
            onSuccess = {
                    msg -> Result.success(msg.toLogoutMapper())
            },
            onFailure = {
                    Result.failure(it)
            }
        )
    }

    override suspend fun listAllUserDispensers(accessToken: String): Result<DispenserEntity> {
        return data.listAllUserDispensers(accessToken).fold(
            onSuccess = {
                    dispenser -> Result.success(dispenser.toDispenserMapper())
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }
}