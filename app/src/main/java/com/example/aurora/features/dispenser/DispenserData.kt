package com.example.aurora.features.dispenser

import com.example.aurora.features.home.AddDispenserNameErrors

data class DispenserData(
    val name: String = "",
    val id: String = "",
    val containers: List<ContainerItem> = emptyList(),
    val renameDraft: String = "",
    val isRenameError: AddDispenserNameErrors = AddDispenserNameErrors.NONE,
    val allDispenserNames: List<String> = emptyList(),
    val errorMessage: String? = null,
    val isRenameSuccessful: Boolean = false,
)