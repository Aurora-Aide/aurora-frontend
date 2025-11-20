package com.example.djigit.data.mapper

import com.example.djigit.data.entity.UserEntity
import com.example.djigit.data.model.UserModel


fun UserModel.toUserEntity(): UserEntity {
    return UserEntity(
        id = this.id,
        email = this.email,
        names = "${this.firstName} ${this.lastName}"
    )
}