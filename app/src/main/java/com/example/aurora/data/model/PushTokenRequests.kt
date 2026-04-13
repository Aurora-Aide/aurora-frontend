package com.example.aurora.data.model

data class RegisterPushTokenRequest(
    val token: String,
    val platform: String = "android",
)

data class DeactivatePushTokenRequest(
    val token: String,
)
