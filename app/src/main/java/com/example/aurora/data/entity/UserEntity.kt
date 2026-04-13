package com.example.aurora.data.entity

data class UserEntity (
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val isSuperuser: Boolean = false,
    val accessToken: String = "",
    val refreshToken: String = "",
)