package com.example.aurora.data.mapper

import com.example.aurora.data.entity.LogoutEntity
import com.example.aurora.data.model.Logout

fun Logout.toLogoutMapper(): LogoutEntity {
    return LogoutEntity(
        message = this.message.orEmpty()
    )
}