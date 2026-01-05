package com.example.aurora.data.model

data class Dispenser(
    val id : String = "",
    val name: String = "",
    val containers: List<ContainerModel>,
)