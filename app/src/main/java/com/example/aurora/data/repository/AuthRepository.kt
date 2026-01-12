package com.example.aurora.data.repository

import com.example.aurora.data.entity.ContainerEntity
import com.example.aurora.data.entity.DeleteUserEntity
import com.example.aurora.data.entity.DeleteDispenserEntity
import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.entity.DispensersEntity
import com.example.aurora.data.entity.ForgotPassEntity
import com.example.aurora.data.entity.LogoutEntity
import com.example.aurora.data.entity.UserEntity
import com.example.aurora.data.entity.ScheduleEntity
import com.example.aurora.data.model.AdminCreateDispenserModelRequest
import com.example.aurora.data.model.AdminDispenserModel
import com.example.aurora.data.model.AdminDispenserModelModel
import com.example.aurora.data.model.AdminUserModel
import com.example.aurora.data.model.CreateScheduleRequest
import com.example.aurora.data.model.UpdateScheduleRequest

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<UserEntity>
    suspend fun signup(email: String, password: String, firstName: String, lastName: String): Result<UserEntity>
    suspend fun addDispenser(modelNumber: String, name: String): Result<DispenserEntity>
    suspend fun forgotPass(email: String): Result<Unit>
    suspend fun resetPass(password: String): Result<ForgotPassEntity>
    suspend fun logout(): Result<LogoutEntity>
    suspend fun listAllUserDispensers(): Result<DispensersEntity>
    suspend fun getUser(): Result<UserEntity>
    suspend fun deleteUser(email: String): Result<DeleteUserEntity>
    suspend fun updateNames(firstName: String?, lastName: String?): Result<UserEntity>
    suspend fun deleteDispenser(name: String): Result<DeleteDispenserEntity>
    suspend fun updatePillName(dispenserName: String, slotNumber: Int, pillName: String): Result<ContainerEntity>
    suspend fun updateDispenserName(currentName: String, newName: String): Result<DispenserEntity>
    suspend fun listSchedules(containerId: Int): Result<List<ScheduleEntity>>
    suspend fun createSchedule(containerId: Int, request: CreateScheduleRequest): Result<ScheduleEntity>
    suspend fun getSchedule(id: Int): Result<ScheduleEntity>
    suspend fun updateSchedule(id: Int, request: UpdateScheduleRequest): Result<ScheduleEntity>
    suspend fun deleteSchedule(id: Int): Result<Unit>
    suspend fun getDispenser(id: String): Result<DispenserEntity>
    // admin
    suspend fun adminListUsers(): Result<List<AdminUserModel>>
    suspend fun adminListDispensers(): Result<List<AdminDispenserModel>>
    suspend fun adminRenameDispenser(id: Int, name: String): Result<AdminDispenserModel>
    suspend fun adminListDispenserModels(): Result<List<AdminDispenserModelModel>>
    suspend fun adminCreateDispenserModel(request: AdminCreateDispenserModelRequest): Result<AdminDispenserModelModel>
}