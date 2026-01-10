package com.example.aurora.data.model

import com.google.gson.annotations.SerializedName

data class UpdateScheduleRequest(
    @SerializedName("day_of_week") val dayOfWeek: Int? = null,
    @SerializedName("hour") val hour: Int? = null,
    @SerializedName("minute") val minute: Int? = null,
    @SerializedName("repeat") val repeat: Boolean? = null
)

