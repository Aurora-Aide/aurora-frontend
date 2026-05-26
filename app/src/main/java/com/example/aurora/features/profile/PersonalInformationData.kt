package com.example.aurora.features.profile

import androidx.annotation.StringRes
import com.example.aurora.features.signup.SignupNamesErrors
import com.example.aurora.ui.UiMessage

data class PersonalInformationData(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val isAdmin: Boolean = false,
    val isUpdateNamesSuccessful: Boolean = false,
    val isFirstNameError: SignupNamesErrors = SignupNamesErrors.NONE,
    val isLastNameError: SignupNamesErrors = SignupNamesErrors.NONE,
    val isLoading: Boolean = false,
    @StringRes val errorMessage: Int = UiMessage.NONE,
)
