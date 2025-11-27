package com.example.aurora.data.model

data class Dispensers (
    val id : Int,
    val modelNumber: String = "",
    val owner: UserModel,
    val name: String = "",
)