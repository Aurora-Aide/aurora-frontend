package com.example.aurora.features.signup


import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.R
import com.example.aurora.domain.usecase.SignupUseCase
import com.example.aurora.features.login.LoginEmailErrors
import com.example.aurora.features.login.LoginPasswordErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignupViewModel(private val signupUseCase: SignupUseCase): ViewModel() {
    private val _signup = MutableStateFlow(SignupData())

    val signup = _signup.asStateFlow()

    fun email(text: String) {
        _signup.update{
            it.copy(email = text)
        }
    }

    fun password(text: String) {
        _signup.update{
            it.copy(password = text)
        }
    }

    fun passwordRepeat(text: String) {
        _signup.update{
            it.copy(passwordRepeat = text)
        }
    }

    fun firstName(text: String) {
        _signup.update{
            it.copy(firstName = text)
        }
    }

    fun lastName(text: String) {
        _signup.update{
            it.copy(lastName = text)
        }
    }

    fun signup(){
        viewModelScope.launch{
            signupUseCase.invoke(_signup.value.email, _signup.value.password, _signup.value.firstName, _signup.value.lastName).fold(
                onSuccess = {
                    Log.d("TAG", "signup request")
                    if(_signup.value.firstName.isNotEmpty() && _signup.value.lastName.isNotEmpty()){
                        firstName(_signup.value.firstName)
                        lastName(_signup.value.lastName)
                    } else{
                        _signup.update {
                            it.copy(isSignupSuccessful = true)
                        }
                    }
                },
                onFailure = {

                }
            )
        }
    }

    fun resetSignup(){
        _signup.update {
            it.copy(isSignupSuccessful = false)
        }
    }

    private fun isPasswordValid(): LoginPasswordErrors {
        return if(_signup.value.password.isEmpty()){
            LoginPasswordErrors.EMPTY_PASSWORD
        } else if(!Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\p{Punct}]{8,}$")
                .matches(_signup.value.password)){
            LoginPasswordErrors.INVALID_PASSWORD
        } else{
            LoginPasswordErrors.NONE
        }
    }

    private fun passwordsMatch(): LoginPasswordErrors {
        return if(_signup.value.passwordRepeat.isEmpty()){
            LoginPasswordErrors.EMPTY_PASSWORD
        }  else if(_signup.value.passwordRepeat != _signup.value.password){
            LoginPasswordErrors.NOT_MATCHING
        } else{
            LoginPasswordErrors.NONE
        }
    }

    private fun isEmailValid(): LoginEmailErrors {
        return if( _signup.value.email.isEmpty()){
            LoginEmailErrors.EMPTY_EMAIL
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_signup.value.email).matches()){
            LoginEmailErrors.INVALID_EMAIL
        } else{
            LoginEmailErrors.NONE
        }
    }

    private fun isNameValid( name: String): SignupNamesErrors {
        return if(name.isEmpty()){
            SignupNamesErrors.EMPTY_NAME
        } else if (!Regex("^[\\p{L}\\p{N} _-]{3,}$")
                .matches(name)){
            SignupNamesErrors.INVALID_NAME
        } else{
            SignupNamesErrors.NONE
        }
    }

    fun validateFirstStep(){
        val emailValid = isEmailValid()
        val passwordValid = isPasswordValid()
        val passwordRepeatValid = passwordsMatch()

        if( emailValid == LoginEmailErrors.NONE &&
            passwordValid == LoginPasswordErrors.NONE &&
            passwordRepeatValid == LoginPasswordErrors.NONE )
        {
            _signup.update {
                it.copy(isEmailError = LoginEmailErrors.NONE,
                    isPasswordError = LoginPasswordErrors.NONE,
                    isPasswordRepeatError = LoginPasswordErrors.NONE,
                    isFirstStep = false)
            }
        } else{
            _signup.update {
                it.copy(
                    isEmailError = emailValid,
                    isPasswordError = passwordValid,
                    isPasswordRepeatError = passwordRepeatValid
                )
            }
        }
    }

    fun validateSecondStep(){
        val firstNameValid = isNameValid(_signup.value.firstName)
        val lastNameValid = isNameValid(_signup.value.lastName)

        if (
            firstNameValid == SignupNamesErrors.NONE &&
            lastNameValid == SignupNamesErrors.NONE
        ){
            _signup.update {
                it.copy(
                    isFirstNameError = SignupNamesErrors.NONE,
                    isLastNameError = SignupNamesErrors.NONE
                )
            }
            signup()
        } else {
            _signup.update {
                it.copy(
                    isFirstNameError = firstNameValid,
                    isLastNameError = lastNameValid
                )
            }
        }


    }

    fun onBackClick(){
        _signup.update {
            it.copy(isFirstStep = true)
        }
    }
}

enum class SignupNamesErrors(val value: Int? = null){
    EMPTY_NAME(R.string.error_empty_name),
    INVALID_NAME(R.string.error_wrong_name),
    NONE
}

