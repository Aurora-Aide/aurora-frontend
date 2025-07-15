package com.example.djigit.data.model

data class Cars (
    val id : Int,
    val brand: String = "",
    val model: String = "",
    val licensePlate: String = "",
    val owner: UserModel,
)