package com.example.aurora.data.model

data class Dispensers (
    val id : Int,
    val name: String = "",
    val owner: UserModel,
    val containers: ContainerModel,
)