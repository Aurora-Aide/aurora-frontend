package com.example.aurora.features.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PersonalInformationViewModel: ViewModel() {
    private val _personalInformation = MutableStateFlow(PersonalInformationData())
    val personalInformation = _personalInformation.asStateFlow()

    fun email(text: String) {
        _personalInformation.update{
            it.copy(email = text)
        }
    }

    fun password(text: String) {
        _personalInformation.update{
            it.copy(password = text)
        }
    }

    fun firstName(text: String) {
        _personalInformation.update{
            it.copy(firstName = text)
        }
    }

    fun lastName(text: String) {
        _personalInformation.update{
            it.copy(lastName = text)
        }
    }

    fun isPasswordVisible() {
        _personalInformation.update{
            it.copy(isPasswordVisible = it.isPasswordVisible.not())
        }
    }
}