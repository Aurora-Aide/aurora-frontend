package com.example.aurora.data.model

data class Dispenser(
    val id : Int,
    val name: String = "",
    //val owner: UserModel,
    val containers: List<ContainerModel>,
)