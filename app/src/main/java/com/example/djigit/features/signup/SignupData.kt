package com.example.djigit.features.signup

import com.example.djigit.features.login.LoginEmailErrors
import com.example.djigit.features.login.LoginPasswordErrors

data class SignupData (
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val isSignupSuccessful: Boolean = false,
    val isEmailError: LoginEmailErrors = LoginEmailErrors.NONE,
    val isPasswordError: LoginPasswordErrors = LoginPasswordErrors.NONE,
    val isFirstStep:Boolean = true,
    val brand: String = "",
    val model: String = "",
    val licensePlate: String = "",
)