package com.example.djigit.data.model

data class CarModel (
    val id: Int,
    val brand: String = "",
    val model: String = "",
    val licensePlate: String = "",
    val owner: UserModel,
)