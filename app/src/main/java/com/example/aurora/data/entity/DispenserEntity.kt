package com.example.aurora.data.entity

import com.example.aurora.data.model.UserModel

data class DispenserEntity(
    val id: Int,
    val brand: String = "",
    val model: String = "",
    val licensePlate: String = "",
    val owner: UserModel,
)