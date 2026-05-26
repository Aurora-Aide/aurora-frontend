package com.example.aurora.features.dispenser

import androidx.annotation.StringRes
import com.example.aurora.ui.UiMessage

data class ScheduleFormState(
    val dayOfWeek: Int = -1,
    val hour: Int = -1,
    val minute: Int = -1,
    val repeat: Boolean = false,
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isValid: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
)

