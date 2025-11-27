package com.example.aurora.features.signup

import com.example.aurora.features.login.LoginEmailErrors
import com.example.aurora.features.login.LoginPasswordErrors

data class SignupData (
    val email: String = "",
    val password: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val isSignupSuccessful: Boolean = false,
    val isEmailError: LoginEmailErrors = LoginEmailErrors.NONE,
    val isPasswordError: LoginPasswordErrors = LoginPasswordErrors.NONE,
    var isFirstStep:Boolean = true,
    val modelNumber: String = "",
    val dispenserName: String = "",
)