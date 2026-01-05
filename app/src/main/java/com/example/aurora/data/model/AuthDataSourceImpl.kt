package com.example.aurora.data.model

import com.example.aurora.data.api.RetrofitAPI
import com.example.aurora.data.requestBody
import com.example.aurora.data.sorce.AuthDataSource
import com.example.aurora.features.forgotPassword.ForgotPassVariables
import com.example.aurora.features.forgotPassword.ResetPassVariables
import com.example.aurora.features.home.DispenserVariables
import com.example.aurora.features.login.LoginVariables
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

    override suspend fun resetPass(password: String): Result<ForgotPass> {
        return requestBody(retrofit.resetPass(ResetPassVariables(password)))
    }

    override suspend fun logout(refreshToken: Refresh): Result<Logout> {
        return try {
            requestBody(retrofit.logout(refreshToken))
        } catch (e: java.net.ProtocolException) {
            // Handle HTTP 205 with body (backend bug, but logout succeeds)
            // HTTP 205 means "Reset Content" - action succeeded
            if (e.message?.contains("HTTP 205") == true) {
                Result.success(Logout(message = "Logged out successfully"))
            } else {
                Result.failure(e)
            }
        }
    }

    override suspend fun listAllUserDispensers(): Result<Dispensers> {
        return requestBody(retrofit.listAllUserDispensers()).map { list ->
            Dispensers(dispensers = list)
        }
    }

    override suspend fun getUser(): Result<UserModel> {
        return requestBody(retrofit.getUser())
    }

    override suspend fun refreshToken(refreshToken: Refresh): Result<AccessToken> {
        return requestBody(retrofit.refreshToken(refreshToken))
    }

    override suspend fun addDispenser(modelNumber: String, name: String, accessToken: String): Result<Dispenser> {
        return requestBody(retrofit.registerDispenser(DispenserVariables(modelNumber, name)))
    }

    override suspend fun deleteUser(email: String): Result<DeleteUserResponse> {
        return requestBody(retrofit.deleteUser(DeleteUserRequest(email)))
    }

    override suspend fun updateNames(firstName: String?, lastName: String?): Result<UserModel> {
        return requestBody(retrofit.updateNames(UpdateNamesRequest(firstName, lastName)))
    }

    override suspend fun deleteDispenser(name: String): Result<DeleteDispenserResponse> {
        return requestBody(retrofit.deleteDispenser(name))
    }

    override suspend fun updatePillName(request: UpdatePillNameRequest): Result<ContainerModel> {
        return requestBody(retrofit.updatePillName(request))
    }

    override suspend fun updateDispenserName(request: UpdateDispenserNameRequest): Result<Dispenser> {
        return requestBody(retrofit.updateDispenserName(request))
    }

    override suspend fun listSchedules(containerId: Int): Result<List<ScheduleModel>> {
        return requestBody(retrofit.listSchedules(containerId))
    }

    override suspend fun createSchedule(containerId: Int, request: CreateScheduleRequest): Result<ScheduleModel> {
        return requestBody(retrofit.createSchedule(containerId, request))
    }

    override suspend fun getSchedule(id: Int): Result<ScheduleModel> {
        return requestBody(retrofit.getSchedule(id))
    }

    override suspend fun updateSchedule(id: Int, request: UpdateScheduleRequest): Result<ScheduleModel> {
        return requestBody(retrofit.updateSchedule(id, request))
    }

    override suspend fun deleteSchedule(id: Int): Result<Unit> {
        return requestBody(retrofit.deleteSchedule(id))
    }

    override suspend fun getDispenser(id: String): Result<Dispenser> {
        return requestBody(retrofit.getDispenser(id))
    }
}