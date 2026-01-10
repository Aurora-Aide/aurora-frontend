package com.example.aurora.features.dispenser

import com.example.aurora.features.home.AddDispenserNameErrors

data class ContainerData(
    val dispenserName: String = "",
    val slotNumber: Int = 0,
    val pillName: String = "",
    val renameDraft: String = "",
    val isUpdating: Boolean = false,
    val errorMessage: String? = null,
    val isRenameSuccessful: Boolean = false,
    val isRenameError: AddDispenserNameErrors = AddDispenserNameErrors.NONE,
    val containerId: Int = 0,
    val schedules: List<ScheduleData> = emptyList()
)