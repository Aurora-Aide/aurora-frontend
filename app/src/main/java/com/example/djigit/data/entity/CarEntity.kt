package com.example.djigit.data.entity

import com.example.djigit.data.model.UserModel

data class CarEntity(
    val id: Int,
    val brand: String = "",
    val model: String = "",
    val licensePlate: String = "",
    val owner: UserModel,
)