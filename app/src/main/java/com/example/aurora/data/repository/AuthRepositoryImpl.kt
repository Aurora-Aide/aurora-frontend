package com.example.aurora.data.repository

import android.util.Log
import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.entity.DispensersEntity
import com.example.aurora.data.entity.ForgotPassEntity
import com.example.aurora.data.entity.LogoutEntity
import com.example.aurora.data.entity.DeleteUserEntity
import com.example.aurora.data.entity.UserEntity
import com.example.aurora.data.entity.DeleteDispenserEntity
import com.example.aurora.data.mapper.toDispenserMapper
import com.example.aurora.data.mapper.toDispensersMapper
import com.example.aurora.data.mapper.toForgotPassMapper
import com.example.aurora.data.mapper.toLogoutMapper
import com.example.aurora.data.mapper.toDeleteUserMapper
import com.example.aurora.data.mapper.toUserEntity
import com.example.aurora.data.mapper.toDeleteDispenserMapper
import com.example.aurora.data.model.Refresh
import com.example.aurora.data.sorce.AuthDataSource

class AuthRepositoryImpl(
    private val data: AuthDataSource,
    private val tokenStorage: TokenStorage): AuthRepository {

    override suspend fun login(email: String, password: String): Result<UserEntity> {
        return data.login(email, password).map { token ->
            Log.d("TOKEN_SAVE", "access len=${token.access.length}, refresh len=${token.refresh.length}")
            tokenStorage.saveTokens(token.access, token.refresh)
            token.user.toUserEntity()
        }
    }

    override suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<UserEntity> {
        return data.signup(email, password, firstName, lastName).map { token ->
            Log.d("TOKEN_SAVE", "access len=${token.access.length}, refresh len=${token.refresh.length}")
            tokenStorage.saveTokens(token.access, token.refresh)
            token.user.toUserEntity()
        }

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

    override suspend fun resetPass(password: String): Result<ForgotPassEntity> {
        return data.resetPass(password).fold(
            onSuccess = {
                    msg -> Result.success(msg.toForgotPassMapper())
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun logout(): Result<LogoutEntity> {
        val refreshToken = tokenStorage.getRefreshToken()
            ?: return Result.failure(Exception("No refresh token"))

        return try {
            data.logout(Refresh(refreshToken)).map { msg ->
                tokenStorage.clearTokens()
                msg.toLogoutMapper()
            }
        } catch (e: java.net.ProtocolException) {
            // Handle HTTP 205 with body (backend bug, but logout succeeds)
            // HTTP 205 means "Reset Content" - action succeeded
            if (e.message?.contains("HTTP 205") == true) {
                tokenStorage.clearTokens()
                Result.success(LogoutEntity(message = "Logged out successfully"))
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun getUser(): Result<UserEntity> {
        return data.getUser().fold(
            onSuccess = {
                    user -> Result.success(user.toUserEntity())
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun listAllUserDispensers(): Result<DispensersEntity> {
        return data.listAllUserDispensers().fold(
            onSuccess = {
                    dispenser -> Result.success(dispenser.toDispensersMapper())
            },
            onFailure = {
                Result.failure(it)
            }
        )
    }

    override suspend fun deleteUser(email: String): Result<DeleteUserEntity> {
        return data.deleteUser(email).fold(
            onSuccess = { 
                msg -> Result.success(msg.toDeleteUserMapper())
             },
            onFailure = { 
                Result.failure(it)
             }
        )
    }

    override suspend fun updateNames(firstName: String?, lastName: String?): Result<UserEntity> {
        return data.updateNames(firstName, lastName).fold(
            onSuccess = { 
                user -> Result.success(user.toUserEntity()) 
            },
            onFailure = { 
                Result.failure(it)
             }
        )
    }

    override suspend fun deleteDispenser(name: String): Result<DeleteDispenserEntity> {
        return data.deleteDispenser(name).fold(
            onSuccess = {
                 msg -> Result.success(msg.toDeleteDispenserMapper())
                 },
            onFailure = { 
                Result.failure(it) 
            }
        )
    }
}