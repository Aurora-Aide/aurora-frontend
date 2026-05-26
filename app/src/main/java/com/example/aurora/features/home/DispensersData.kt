package com.example.aurora.features.home

import androidx.annotation.StringRes
import com.example.aurora.data.model.Dispenser
import com.example.aurora.ui.UiMessage

data class DispensersData(
    val dispensers: List<Dispenser> = emptyList(),
    @StringRes val errorMessage: Int = UiMessage.NONE,
)
