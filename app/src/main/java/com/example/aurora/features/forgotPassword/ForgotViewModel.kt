package com.example.aurora.features.forgotPassword

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.R
import com.example.aurora.data.error.toUiMessage
import com.example.aurora.domain.usecase.ForgotPassUseCase
import com.example.aurora.domain.usecase.ResetPassUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotViewModel(private val forgotUseCase: ForgotPassUseCase, private val resetPassUseCase: ResetPassUseCase): ViewModel() {
    private val _data = MutableStateFlow(ForgotPassData())
    val data = _data.asStateFlow()

    fun email(text: String){
        _data.update {
            it.copy(email = text, errorMessage = "")
        }
    }

    fun password(text: String){
        _data.update {
            it.copy(password = text, errorMessage = "")
        }
    }

    fun repeat(text: String){
        _data.update {
            it.copy(repeat = text, errorMessage = "")
        }
    }

    private fun isPasswordValid(): ForgotPasswordErrors {
        return if(_data.value.password.isEmpty()){
            ForgotPasswordErrors.EMPTY_PASSWORD
        } else if(!Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\p{Punct}]{8,}$")
                .matches(_data.value.password)){
            ForgotPasswordErrors.INVALID_PASSWORD
        } else{
            ForgotPasswordErrors.NONE
        }
    }

    private fun isRepeatValid(): ForgotRepeatErrors {
        return if(_data.value.repeat.isEmpty()){
            ForgotRepeatErrors.EMPTY_PASSWORD
        } else if(!Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\p{Punct}]{8,}$")
                .matches(_data.value.repeat)){
            ForgotRepeatErrors.INVALID_PASSWORD
        } else if(_data.value.repeat != _data.value.password){
            ForgotRepeatErrors.DOESNT_MATCH
        } else{
            ForgotRepeatErrors.NONE
        }
    }


    private fun isEmailValid(): ForgotEmailErrors {
        return if( _data.value.email.isEmpty()){
            ForgotEmailErrors.EMPTY_EMAIL
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_data.value.email).matches()){
            ForgotEmailErrors.INVALID_EMAIL
        } else{
            ForgotEmailErrors.NONE
        }
    }

    private fun forgotPass(){
        viewModelScope.launch{
            _data.update { it.copy(errorMessage = "") }
            forgotUseCase.invoke(_data.value.email).fold(
                onSuccess = {
                    _data.update{
                        it.copy(isFirstStep = false, errorMessage = "")
                    }
                },
                onFailure = { error ->
                    _data.update { it.copy(errorMessage = error.toUiMessage()) }
                }
            )
        }
    }

    private fun resetPass(){
        viewModelScope.launch{
            _data.update { it.copy(errorMessage = "") }
            resetPassUseCase.invoke(_data.value.password, _data.value.token).fold(
                onSuccess = {
                    _data.update{
                        it.copy(resultPageGood = true, errorMessage = "")
                    }
                },
                onFailure = { error ->
                    _data.update{
                        it.copy(resultPageBad = true, errorMessage = error.toUiMessage())
                    }
                }
            )
        }
    }

    fun validateEmail(){
        _data.update { it.copy(errorMessage = "") }
        val emailValid = isEmailValid()
        if(emailValid == ForgotEmailErrors.NONE){
            _data.update{
                it.copy(isEmailError = ForgotEmailErrors.NONE)
            }
            forgotPass()
        } else{
            _data.update {
                it.copy(isEmailError = emailValid)
            }
        }
    }

    fun validatePassword(){
        _data.update { it.copy(errorMessage = "") }
        val passwordValid = isPasswordValid()
        val repeatValid = isRepeatValid()
        if( passwordValid == ForgotPasswordErrors.NONE && repeatValid == ForgotRepeatErrors.NONE){
            _data.update {
                it.copy(isPasswordError = ForgotPasswordErrors.NONE, isRepeatError = ForgotRepeatErrors.NONE)
            }
            resetPass()
        } else{
            _data.update {
                it.copy(isPasswordError = passwordValid, isRepeatError = repeatValid)
            }
        }
    }
}

enum class ForgotEmailErrors(val value: Int? = null){
    EMPTY_EMAIL(R.string.error_empty_email),
    INVALID_EMAIL(R.string.error_wrong_email),
    NONE()
}

enum class ForgotPasswordErrors(val value: Int? = null){
    EMPTY_PASSWORD(R.string.error_empty_password),
    INVALID_PASSWORD(R.string.error_wrong_password),

    NONE()
}

enum class ForgotRepeatErrors(val value: Int? = null){
    EMPTY_PASSWORD(R.string.error_empty_password),
    INVALID_PASSWORD(R.string.error_wrong_password),
    DOESNT_MATCH(R.string.error_mismatch_passwords),
    NONE()
}