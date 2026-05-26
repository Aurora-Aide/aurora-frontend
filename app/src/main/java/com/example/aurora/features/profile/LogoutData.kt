package com.example.aurora.features.profile

import androidx.annotation.StringRes
import com.example.aurora.ui.UiMessage

data class LogoutData (
    val refresh: String = "",
    val isLoggedOut: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
)