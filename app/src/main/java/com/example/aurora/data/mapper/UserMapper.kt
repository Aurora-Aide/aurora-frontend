package com.example.aurora.data.mapper

import com.example.aurora.data.entity.UserEntity
import com.example.aurora.data.model.UserModel


fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        email = this.email,
        firstName = this.firstName,
        lastName = this.lastName,
    )
}