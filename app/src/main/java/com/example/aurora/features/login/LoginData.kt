package com.example.aurora.features.login

data class LoginData(
    val email: String = "",
    val password: String = "",
    val isLoginSuccessful: Boolean = false,
    val isEmailError: LoginEmailErrors = LoginEmailErrors.NONE,
    val isPasswordError: LoginPasswordErrors = LoginPasswordErrors.NONE,
    val name: String = "",  // TODO use this in home
//    val googleProfilePictureUrl: String = "",
//    val navigationState: NavController.Companion,
)
