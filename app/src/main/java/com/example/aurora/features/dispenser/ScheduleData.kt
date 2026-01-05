package com.example.aurora.features.dispenser

import com.example.aurora.R

data class ScheduleData(
    val day: DaysOfWeek = DaysOfWeek.EMPTY,
    val hour: Int = 0,
    val minutes: Int = 0,
    val repeating: Boolean = false,
)

enum class DaysOfWeek(val value: Int? = null){
    EMPTY(R.string.empty),
    MONDAY(R.string.monday),
    TUESDAY(R.string.tuesday),
    WEDNESDAY(R.string.wednesday),
    THURSDAY(R.string.thursday),
    FRIDAY(R.string.friday),
    SATURDAY(R.string.saturday),
    SUNDAY(R.string.sunday),
}