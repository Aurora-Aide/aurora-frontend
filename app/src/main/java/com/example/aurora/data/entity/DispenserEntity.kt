package com.example.aurora.data.entity

import com.example.aurora.data.model.ContainerModel
import com.example.aurora.data.model.UserModel

data class DispenserEntity(
    val id: Int,
    val name: String = "",
    val owner: UserModel,
    val containers: ContainerModel,
)