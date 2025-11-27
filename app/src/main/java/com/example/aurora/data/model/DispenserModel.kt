package com.example.aurora.data.model

data class DispenserModel (
    val id: Int,
    val modelNumber: String = "",
    val owner: UserModel,
)