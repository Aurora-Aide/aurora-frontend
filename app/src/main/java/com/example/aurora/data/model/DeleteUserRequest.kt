package com.example.aurora.data.model

import com.google.gson.annotations.SerializedName

data class DeleteUserRequest(
    @SerializedName("user_email")
    val email: String
)

