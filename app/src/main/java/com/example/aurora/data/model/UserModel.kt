package com.example.aurora.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    val email: String = "",
    @SerializedName("first_name") val firstName: String = "",
    @SerializedName("last_name") val lastName: String = "",
    @SerializedName("is_superuser") val isSuperuser: Boolean = false
)