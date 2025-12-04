package com.example.aurora.data.entity

import com.example.aurora.data.model.UserModel

data class DispenserEntity(
    val id: Int,
    val modelNumber: String = "",
    val owner: UserModel,
    val name: String = "",
)