package com.example.djigit.data.model

data class Tokens (
    val accessToken: String = "",
    val refreshToken: String = "",
    val user: UserModel,
)

