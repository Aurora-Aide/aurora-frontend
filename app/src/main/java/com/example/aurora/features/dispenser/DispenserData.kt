package com.example.aurora.features.dispenser

import androidx.annotation.StringRes
import com.example.aurora.features.home.AddDispenserNameErrors
import com.example.aurora.ui.UiMessage

data class DispenserData(
    val name: String = "",
    val id: String = "",
    val containers: List<ContainerItem> = emptyList(),
    val renameDraft: String = "",
    val isRenameError: AddDispenserNameErrors = AddDispenserNameErrors.NONE,
    val allDispenserNames: List<String> = emptyList(),
    @StringRes val errorMessage: Int = UiMessage.NONE,
    val statusMessage: String = "",
    val isRenameSuccessful: Boolean = false,
)