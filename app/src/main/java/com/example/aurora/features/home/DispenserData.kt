package com.example.aurora.features.home

import androidx.annotation.StringRes
import com.example.aurora.ui.UiMessage

data class DispenserData (
    val id: String = "",
    val name: String = "",
    val isAddDispenserSuccessful: Boolean = false,
    val isIDError: AddDispenserIDErrors = AddDispenserIDErrors.NONE,
    val isNameError: AddDispenserNameErrors = AddDispenserNameErrors.NONE,
    val allDispenserNames: List<String> = emptyList(),
    val allDispenserIds: List<String> = emptyList(),
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
    //val isCountError: Boolean = false,
)