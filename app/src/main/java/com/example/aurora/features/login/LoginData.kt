package com.example.aurora.features.login

import androidx.annotation.StringRes
import com.example.aurora.ui.UiMessage

data class LoginData(
    val email: String = "",
    val password: String = "",
    val isLoginSuccessful: Boolean = false,
    val isEmailError: LoginEmailErrors = LoginEmailErrors.NONE,
    val isPasswordError: LoginPasswordErrors = LoginPasswordErrors.NONE,
    val firstName: String = "",  // TODO use this in home
    val lastName: String = "",
    val id: String = "",
    val isAdmin: Boolean = false,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
)
