package com.example.aurora.data.sorce

import com.example.aurora.data.model.AccessToken
import com.example.aurora.data.model.Dispenser
import com.example.aurora.data.model.Dispensers
import com.example.aurora.data.model.ForgotPass
import com.example.aurora.data.model.Logout
import com.example.aurora.data.model.Refresh
import com.example.aurora.data.model.Tokens
import com.example.aurora.data.model.UserModel
import com.example.aurora.data.model.DeleteUserResponse
import com.example.aurora.data.model.DeleteDispenserResponse
import com.example.aurora.data.model.UpdatePillNameRequest
import com.example.aurora.data.model.UpdateDispenserNameRequest
import com.example.aurora.data.model.ContainerModel
import com.example.aurora.data.model.ScheduleModel
import com.example.aurora.data.model.CreateScheduleRequest
import com.example.aurora.data.model.UpdateScheduleRequest

interface AuthDataSource {
    suspend fun login(email: String, password: String): Result<Tokens>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<Tokens>
    suspend fun addDispenser(modelNumber: String, name: String, accessToken: String): Result<Dispenser>
    suspend fun forgotPass(email: String): Result<Unit>
    suspend fun resetPass(password: String): Result<ForgotPass>
    suspend fun logout(refreshToken: Refresh): Result<Logout>
    suspend fun listAllUserDispensers(): Result<Dispensers>
    suspend fun getUser(): Result<UserModel>
    suspend fun refreshToken(refreshToken: Refresh): Result<AccessToken>
    suspend fun deleteUser(email: String): Result<DeleteUserResponse>
    suspend fun updateNames(firstName: String?, lastName: String?): Result<UserModel>
    suspend fun deleteDispenser(name: String): Result<DeleteDispenserResponse>
    suspend fun updatePillName(request: UpdatePillNameRequest): Result<ContainerModel>
    suspend fun updateDispenserName(request: UpdateDispenserNameRequest): Result<Dispenser>
    suspend fun listSchedules(containerId: Int): Result<List<ScheduleModel>>
    suspend fun createSchedule(containerId: Int, request: CreateScheduleRequest): Result<ScheduleModel>
    suspend fun getSchedule(id: Int): Result<ScheduleModel>
    suspend fun updateSchedule(id: Int, request: UpdateScheduleRequest): Result<ScheduleModel>
    suspend fun deleteSchedule(id: Int): Result<Unit>
    suspend fun getDispenser(id: String): Result<Dispenser>
}