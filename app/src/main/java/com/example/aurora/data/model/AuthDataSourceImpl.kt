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
        val response = retrofit.deleteSchedule(id)
        return if (response.isSuccessful) {
            Result.success(Unit)
        } else {
            Result.failure(Throwable(response.errorBody().toString()))
        }
    }

    override suspend fun getDispenser(id: String): Result<Dispenser> {
        return requestBody(retrofit.getDispenser(id))
    }
}