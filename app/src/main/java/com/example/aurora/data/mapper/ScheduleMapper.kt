package com.example.aurora.data.mapper

import com.example.aurora.data.entity.ScheduleEntity
import com.example.aurora.data.model.ScheduleModel

fun ScheduleModel.toScheduleEntity(): ScheduleEntity {
    return ScheduleEntity(
        id = id,
        dayOfWeek = dayOfWeek,
        hour = hour,
        minute = minute,
        repeat = repeat
    )
}