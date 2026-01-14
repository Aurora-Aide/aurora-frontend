package com.example.aurora.data.mapper

import com.example.aurora.data.entity.DeleteUserEntity
import com.example.aurora.data.model.DeleteUserResponse

fun DeleteUserResponse.toDeleteUserMapper(): DeleteUserEntity {
    return DeleteUserEntity(detail = this.detail)
}

