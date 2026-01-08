package com.example.aurora.data.model

data class Tokens (
    val accessToken: String = "",
    val refreshToken: String = "",
    val user: UserModel,
)

