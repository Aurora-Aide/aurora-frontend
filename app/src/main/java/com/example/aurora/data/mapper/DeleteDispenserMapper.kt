package com.example.aurora.data.mapper

import com.example.aurora.data.entity.DeleteDispenserEntity
import com.example.aurora.data.model.DeleteDispenserResponse

fun DeleteDispenserResponse.toDeleteDispenserMapper(): DeleteDispenserEntity {
    return DeleteDispenserEntity(detail = this.detail)
}

