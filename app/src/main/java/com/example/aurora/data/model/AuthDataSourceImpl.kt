package com.example.aurora.data.model

import com.example.aurora.data.api.RetrofitAPI
import com.example.aurora.data.error.AppException
import com.example.aurora.data.parseApiError
import com.example.aurora.data.requestBody
import com.example.aurora.data.safeRequest
import com.example.aurora.data.safeResponse
import com.example.aurora.data.sorce.AuthDataSource
import com.example.aurora.features.forgotPassword.ForgotPassVariables
import com.example.aurora.features.forgotPassword.ResetPassVariables
import com.example.aurora.features.home.DispenserVariables
import com.example.aurora.features.login.LoginVariables
import com.example.aurora.features.signup.SignupVariables

class AuthDataSourceImpl(private val retrofit: RetrofitAPI): AuthDataSource {
    override suspend fun login(email: String, password: String): Result<Tokens> {
        return safeRequest { retrofit.login(LoginVariables(email, password)) }
    }

    override suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<Tokens> {
        return safeRequest { retrofit.signup(SignupVariables(email, password, firstName, lastName)) }
    }

    override suspend fun forgotPass(email: String): Result<Unit> {
        return safeRequest { retrofit.forgotPass(ForgotPassVariables(email)) }
    }

    override suspend fun resetPass(password: String): Result<ForgotPass> {
        return safeRequest { retrofit.resetPass(ResetPassVariables(password)) }
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
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun listAllUserDispensers(): Result<Dispensers> {
        return safeRequest { retrofit.listAllUserDispensers() }.map { list ->
            Dispensers(dispensers = list)
        }
    }

    override suspend fun getUser(): Result<UserModel> {
        return safeRequest { retrofit.getUser() }
    }

    override suspend fun refreshToken(refreshToken: Refresh): Result<AccessToken> {
        return safeRequest { retrofit.refreshToken(refreshToken) }
    }

    override suspend fun addDispenser(modelNumber: String, name: String): Result<Dispenser> {
        return safeRequest { retrofit.registerDispenser(DispenserVariables(modelNumber, name)) }
    }

    override suspend fun deleteUser(email: String): Result<DeleteUserResponse> {
        return safeRequest { retrofit.deleteUser(DeleteUserRequest(email)) }
    }

    override suspend fun updateNames(firstName: String?, lastName: String?): Result<UserModel> {
        return safeRequest { retrofit.updateNames(UpdateNamesRequest(firstName, lastName)) }
    }

    override suspend fun deleteDispenser(name: String): Result<DeleteDispenserResponse> {
        return safeRequest { retrofit.deleteDispenser(name) }
    }

    override suspend fun updatePillName(request: UpdatePillNameRequest): Result<ContainerModel> {
        return safeRequest { retrofit.updatePillName(request) }
    }

    override suspend fun updateDispenserName(request: UpdateDispenserNameRequest): Result<Dispenser> {
        return safeRequest { retrofit.updateDispenserName(request) }
    }

    override suspend fun listSchedules(containerId: Int): Result<List<ScheduleModel>> {
        return safeRequest { retrofit.listSchedules(containerId) }
    }

    override suspend fun createSchedule(containerId: Int, request: CreateScheduleRequest): Result<ScheduleModel> {
        return safeRequest { retrofit.createSchedule(containerId, request) }
    }

    override suspend fun getSchedule(id: Int): Result<ScheduleModel> {
        return safeRequest { retrofit.getSchedule(id) }
    }

    override suspend fun updateSchedule(id: Int, request: UpdateScheduleRequest): Result<ScheduleModel> {
        return safeRequest { retrofit.updateSchedule(id, request) }
    }

    override suspend fun deleteSchedule(id: Int): Result<Unit> {
        return safeResponse { retrofit.deleteSchedule(id) }.fold(
            onSuccess = { response ->
                if (response.isSuccessful) {
                    Result.success(Unit)
                } else {
                    Result.failure(AppException(parseApiError(response)))
                }
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }

    override suspend fun getDispenser(id: String): Result<Dispenser> {
        return safeRequest { retrofit.getDispenser(id) }
    }

    // admin
    override suspend fun adminListUsers(): Result<List<AdminUserModel>> {
        return safeRequest { retrofit.adminListUsers() }
    }

    override suspend fun adminListDispensers(): Result<List<AdminDispenserModel>> {
        return safeRequest { retrofit.adminListDispensers() }
    }

    override suspend fun adminListDispenserModels(): Result<List<AdminDispenserModelModel>> {
        return safeRequest { retrofit.adminListDispenserModels() }
    }

    override suspend fun adminCreateDispenserModel(request: AdminCreateDispenserModelRequest): Result<AdminDispenserModelModel> {
        return safeRequest { retrofit.adminCreateDispenserModel(request) }
    }
}