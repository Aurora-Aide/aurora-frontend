package com.example.aurora.features.profile

data class DispenserData(
    val id: String = "",
    val name: String = "",
    val containers: List<ContainerData>
)