package com.example.aurora.data.mapper

import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.model.Dispensers

fun Dispensers.toDispenserMapper(): DispenserEntity {
    return DispenserEntity(
        id = this.id,
        owner = this.owner,
        name = this.name,
        containers = this.containers
    )
}