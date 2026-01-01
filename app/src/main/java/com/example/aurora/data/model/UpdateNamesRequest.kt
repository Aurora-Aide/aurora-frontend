package com.example.aurora.data.model

import com.google.gson.annotations.SerializedName

data class UpdateNamesRequest(
    @SerializedName("first_name")
    val firstName: String? = null,
    @SerializedName("last_name")
    val lastName: String? = null
)

