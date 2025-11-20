package com.example.aurora.features.forgotPassword

data class ForgotPassData(
    val email: String = "",
    val password: String = "",
    val repeat: String  = "",
    val isEmailError: ForgotEmailErrors = ForgotEmailErrors.NONE,
    val isPasswordError: ForgotPasswordErrors = ForgotPasswordErrors.NONE,
    val isRepeatError: ForgotRepeatErrors = ForgotRepeatErrors.NONE,
    var isFirstStep: Boolean = true,
    val token: String = "",
    val resultPageGood: Boolean = false,
    val resultPageBad: Boolean = false,
)