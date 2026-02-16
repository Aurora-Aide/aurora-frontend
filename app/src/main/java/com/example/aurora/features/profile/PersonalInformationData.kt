package com.example.aurora.features.profile

import com.example.aurora.features.signup.SignupNamesErrors

data class PersonalInformationData(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val isAdmin: Boolean = false,
    val isUpdateNamesSuccessful: Boolean = false,
    val isFirstNameError: SignupNamesErrors = SignupNamesErrors.NONE,
    val isLastNameError: SignupNamesErrors = SignupNamesErrors.NONE,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)
