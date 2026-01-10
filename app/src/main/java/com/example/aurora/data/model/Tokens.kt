package com.example.aurora.data.model

import com.google.gson.annotations.SerializedName

data class Tokens(
    @SerializedName("access") val access: String,
    @SerializedName("refresh") val refresh: String,
    @SerializedName("user") val user: UserModel
)

