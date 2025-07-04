package com.example.djigit.features.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class LoginViewModel: ViewModel() {
    private val _login = MutableStateFlow(LoginVariables())

    val login = _login.asStateFlow()

    fun email(text: String) {
        _login.update{
            it.copy(email = text)
        }
    }

    fun password(text: String) {
        _login.update{
            it.copy(password = text)
        }
    }
}
