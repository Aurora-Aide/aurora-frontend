package com.example.djigit.features.signup


import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.djigit.domain.usecase.CarUseCase
import com.example.djigit.domain.usecase.SignupUseCase
import com.example.djigit.features.login.LoginEmailErrors
import com.example.djigit.features.login.LoginPasswordErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class SignupViewModel(private val signupUseCase: SignupUseCase, private val carUseCase: CarUseCase): ViewModel() {
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
    fun brand(text: String) {
        _signup.update{
            it.copy(brand = text)
        }
    }

    fun licensePlate(text: String) {
        _signup.update{
            it.copy(licensePlate = text)
        }
    }

    fun model(text: String) {
        _signup.update{
            it.copy(model = text)
        }
    }

    fun signup(){
        viewModelScope.launch{
            signupUseCase.invoke(_signup.value.email, _signup.value.password, _signup.value.firstName, _signup.value.lastName).fold(
                onSuccess = {
                    if(_signup.value.brand.isNotEmpty() && _signup.value.model.isNotEmpty() && _signup.value.licensePlate.isNotEmpty()){
                        carUseCase.invoke(_signup.value.brand, _signup.value.model, _signup.value.licensePlate)
                    } else{
                        _signup.update {
                            it.copy(isSignupSuccessful = true)
                        }
                    }
                },
                onFailure = {
                    // to implement
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

    private fun isEmailValid(): LoginEmailErrors {
        return if( _signup.value.email.isEmpty()){
            LoginEmailErrors.EMPTY_EMAIL
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_signup.value.email).matches()){
            LoginEmailErrors.INVALID_EMAIL
        } else{
            LoginEmailErrors.NONE
        }
    }

    fun validate(){
        val emailValid = isEmailValid()
        val passwordValid = isPasswordValid()
        if( emailValid == LoginEmailErrors.NONE && passwordValid == LoginPasswordErrors.NONE){
            _signup.update {
                it.copy(isEmailError = LoginEmailErrors.NONE, isPasswordError = LoginPasswordErrors.NONE, isFirstStep = false)
            }
        } else{
            _signup.update {
                it.copy(isEmailError = emailValid, isPasswordError = passwordValid)
            }
        }
    }

    fun onBackClick(){
        _signup.update {
            it.copy(isFirstStep = true)
        }
    }
}

