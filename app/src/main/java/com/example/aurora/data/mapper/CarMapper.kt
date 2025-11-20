package com.example.aurora.data.mapper

import com.example.aurora.data.entity.DispenserEntity
import com.example.aurora.data.model.Cars

fun Cars.toCarMapper(): DispenserEntity {
    return DispenserEntity(
        id = this.id,
        brand = this.brand,
        model = this.model,
        licensePlate = this.licensePlate,
        owner = this.owner
    )
}