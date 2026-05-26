package com.example.aurora.features.dispenser

import androidx.annotation.StringRes
import com.example.aurora.ui.UiMessage

data class ScheduleDetailData(
    val id: Int = 0,
    val containerName: String = "",
    val dispenserName: String = "",
    val dayOfWeek: Int = -1,
    val hour: Int = -1,
    val minute: Int = -1,
    val repeat: Boolean = false,
    val isLoading: Boolean = false,
    val isEditing: Boolean = false,
    val isSaving: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
    val isSuccess: Boolean = false,
    val isDeleted: Boolean = false
)

