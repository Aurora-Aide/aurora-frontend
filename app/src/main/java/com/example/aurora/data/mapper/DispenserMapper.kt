package com.example.aurora.data.mapper

import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.model.Dispensers

fun Dispensers.toDispenserMapper(): DispenserEntity {
    return DispenserEntity(
        id = this.id,
        modelNumber = this.modelNumber,
        owner = this.owner,
        name = this.name,
    )
}