package com.example.aurora.data.entity

data class ScheduleEntity(
    val id: Int,
    val dayOfWeek: Int,
    val hour: Int,
    val minute: Int,
    val repeat: Boolean
)

