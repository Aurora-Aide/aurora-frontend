package com.example.aurora.data.mapper

import com.example.aurora.data.entity.DispensersEntity
import com.example.aurora.data.model.Dispensers

fun Dispensers.toDispensersMapper(): DispensersEntity {
    return DispensersEntity(
        dispensers = this.dispensers
    )
}