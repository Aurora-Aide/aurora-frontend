package com.example.aurora.data.mapper

import com.example.aurora.data.entity.ContainerEntity
import com.example.aurora.data.model.ContainerModel

fun ContainerModel.toContainerEntity(): ContainerEntity {
    return ContainerEntity(
        id = this.id,
        dispenser = this.dispenser,
        slotNumber = this.slotNumber,
        pillName = this.pillName
    )
}

