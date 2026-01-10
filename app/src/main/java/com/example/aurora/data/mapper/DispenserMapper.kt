package com.example.aurora.data.mapper

import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.model.Dispenser

fun Dispenser.toDispenserMapper(): DispenserEntity {
    return DispenserEntity(
        id = this.id,
        name = this.name,
        containers = this.containers
    )
}