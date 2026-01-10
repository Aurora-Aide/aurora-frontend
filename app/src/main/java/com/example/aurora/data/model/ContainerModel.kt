package com.example.aurora.data.model

import com.google.gson.annotations.SerializedName

data class ContainerModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("dispenser")
    val dispenser: Int, // the dispenser id
    @SerializedName("slot_number")
    val slotNumber: Int,
    @SerializedName("pill_name")
    val pillName: String = "",
)