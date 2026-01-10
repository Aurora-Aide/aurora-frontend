package com.example.aurora.data.entity

import com.example.aurora.data.model.AccessToken
import com.google.firebase.appdistribution.gradle.RefreshToken

data class UserEntity (
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val accessToken: String = "",
    val refreshToken: String = "",
)