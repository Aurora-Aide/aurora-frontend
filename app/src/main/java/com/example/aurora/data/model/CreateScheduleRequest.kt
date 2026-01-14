package com.example.aurora.data.model

import com.google.gson.annotations.SerializedName

data class CreateScheduleRequest(
    @SerializedName("day_of_week") val dayOfWeek: Int,
    @SerializedName("hour") val hour: Int,
    @SerializedName("minute") val minute: Int,
    @SerializedName("repeat") val repeat: Boolean
)

