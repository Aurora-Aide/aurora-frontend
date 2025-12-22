package com.example.aurora.features.profile

import com.example.aurora.data.model.UserModel

data class LogoutData (
    val refreshToken: String = "",
    val accessToken: String = "",
    // val user: UserModel,
)