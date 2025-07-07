package com.example.djigit.features.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.djigit.domain.usecase.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(private val loginUseCase: LoginUseCase): ViewModel() {
    private val _login = MutableStateFlow(LoginData())

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

    fun login(){
        viewModelScope.launch{
            loginUseCase.invoke(_login.value.email, _login.value.password).fold(
                onSuccess = {
                    _login.update {
                        it.copy(isLoginSuccessful = true)
                    }
                },
                onFailure = {

                }
            )
        }

    }

    fun resetLogin(){
        _login.update {
            it.copy(isLoginSuccessful = false)
        }
    }

}
