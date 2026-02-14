package com.example.aurora.features.profile

data class LogoutData (
    val refresh: String = "",
    val isLoggedOut: Boolean = false,
    val errorMessage: String = "",
)