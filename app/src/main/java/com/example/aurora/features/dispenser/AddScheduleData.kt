package com.example.aurora.features.dispenser

data class ScheduleFormState(
    val dayOfWeek: Int = -1,
    val hour: Int = -1,
    val minute: Int = -1,
    val repeat: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isValid: Boolean = false,
    val errorMessage: String = ""
)

