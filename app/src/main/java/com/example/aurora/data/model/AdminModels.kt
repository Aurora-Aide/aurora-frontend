package com.example.aurora.data.model

data class AdminUserModel(
    val id: Int,
    val email: String,
    val first_name: String,
    val last_name: String,
    val is_active: Boolean,
    val is_staff: Boolean
)

data class AdminDispenserOwner(
    val id: Int?,
    val email: String?
)

data class AdminDispenserModelInfo(
    val id: Int?,
    val code: String?,
    val name: String?,
    val slot_count: Int?
)

data class AdminDispenserModel(
    val id: Int,
    val name: String,
    val serial_id: String?,
    val size: String?,
    val owner: AdminDispenserOwner?,
    val model: AdminDispenserModelInfo?
)

data class AdminRenameDispenserRequest(
    val name: String
)

data class AdminDispenserModelModel(
    val id: Int,
    val code: String,
    val name: String,
    val slot_count: Int,
    val serial_prefix: String,
    val next_sequence: Int?
)

data class AdminCreateDispenserModelRequest(
    val code: String,
    val name: String,
    val slot_count: String,
    val serial_prefix: String
)

