package com.example.aurora.data.model

data class ContainerModel(
    val id: Int,
    val dispenser: Int, // the dispensers id
    val slotNumber: Int,
    val pillName: String = "",
    )