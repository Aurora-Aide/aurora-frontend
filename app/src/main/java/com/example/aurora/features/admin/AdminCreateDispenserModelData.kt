package com.example.aurora.features.admin

import androidx.annotation.StringRes
import com.example.aurora.ui.UiMessage

data class AdminCreateDispenserModelData(
    val code: String = "",
    val name: String = "",
    val slotCount: String = "",
    val serialPrefix: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
    val isCodeError: AddModelCodeErrors = AddModelCodeErrors.NONE,
    val isNameError: AddModelNameErrors = AddModelNameErrors.NONE,
    val isSerialPrefixError: AddModelSerialPrefixErrors = AddModelSerialPrefixErrors.NONE,
    val isSlotCountError: AddModelSlotCountErrors = AddModelSlotCountErrors.NONE,
    val allModelsCodes: List<String> = emptyList(),
    val allModelsPrefixes: List<String> = emptyList(),
)
