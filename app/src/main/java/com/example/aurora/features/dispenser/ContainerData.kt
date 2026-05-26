package com.example.aurora.features.dispenser

import androidx.annotation.StringRes
import com.example.aurora.features.home.AddDispenserNameErrors
import com.example.aurora.ui.UiMessage

data class ContainerData(
    val dispenserName: String = "",
    val slotNumber: Int = 0,
    val pillName: String = "",
    val renameDraft: String = "",
    val isUpdating: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
    val isRenameSuccessful: Boolean = false,
    val isRenameError: AddDispenserNameErrors = AddDispenserNameErrors.NONE,
    val containerId: Int = 0,
    val schedules: List<ScheduleData> = emptyList()
)