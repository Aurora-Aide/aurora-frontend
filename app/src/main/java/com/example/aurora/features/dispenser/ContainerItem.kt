package com.example.aurora.features.dispenser

data class ContainerItem(
    val title: String,
    val subtitle: String? = null,
    val slotNumber: Int = 0,
    val containerId: Int = 0,
)