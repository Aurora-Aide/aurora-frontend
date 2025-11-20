package com.example.aurora.features.profile

data class PersonalInformationData(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
)
