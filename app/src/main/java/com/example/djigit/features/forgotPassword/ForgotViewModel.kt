package com.example.djigit.features.forgotPassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.djigit.domain.usecase.ForgotPassUseCase
import com.example.djigit.domain.usecase.ResetPassUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotViewModel(private val forgotUseCase: ForgotPassUseCase, private val resetPassUseCase: ResetPassUseCase): ViewModel() {
    private val _data = MutableStateFlow(ForgotPassData())
    val data = _data.asStateFlow()

    fun email(text: String){
        _data.update {
            it.copy(email = text)
        }
    }

    fun password(text: String){
        _data.update {
            it.copy(password = text)
        }
    }

    fun repeat(text: String){
        _data.update {
            it.copy(repeat = text)
        }
    }

    fun forgotPass(){
        viewModelScope.launch{
            forgotUseCase.invoke(_data.value.email).fold(
                onSuccess = {
                },
                onFailure = {

                }
            )
        }
    }

    fun resetPass(){
        viewModelScope.launch{
            resetPassUseCase.invoke(_data.value.password, _data.value.repeat).fold(
                onSuccess = {
                },
                onFailure = {

                }
            )
        }
    }
}