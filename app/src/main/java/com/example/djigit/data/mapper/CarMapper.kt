package com.example.djigit.data.mapper

import com.example.djigit.data.entity.CarEntity
import com.example.djigit.data.model.Cars

fun Cars.toCarMapper(): CarEntity {
    return CarEntity(
        id = this.id,
        brand = this.brand,
        model = this.model,
        licensePlate = this.licensePlate,
        owner = this.owner
    )
}