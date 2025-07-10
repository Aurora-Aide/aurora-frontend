package com.example.djigit.data.model

import androidx.annotation.IntegerRes

data class UserModel (
    val id: Int,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = ""
)