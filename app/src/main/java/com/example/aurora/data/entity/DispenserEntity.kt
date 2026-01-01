package com.example.aurora.data.entity

import com.example.aurora.data.model.ContainerModel

data class DispenserEntity(
    val id: Int,
    val name: String = "",
    //val owner: UserModel,
    val containers: List<ContainerModel>,
)