package com.example.aurora.features.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.error.toUiMessageRes
import com.example.aurora.ui.UiMessage
import com.example.aurora.domain.usecase.UpdateNamesUseCase
import com.example.aurora.domain.usecase.GetUserUseCase
import com.example.aurora.features.signup.SignupNamesErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalInformationViewModel(
    private val updateNamesUseCase: UpdateNamesUseCase,
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    private val _personalInformation = MutableStateFlow(PersonalInformationData())
    val personalInformation = _personalInformation.asStateFlow()

    init {
        fetchUser()
    }

    fun firstName(text: String) {
        _personalInformation.update{
            it.copy(firstName = text, errorMessage = UiMessage.NONE)
        }
    }

    fun lastName(text: String) {
        _personalInformation.update{
            it.copy(lastName = text, errorMessage = UiMessage.NONE)
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

    fun resetUpdateNames(){
        _personalInformation.update {
            it.copy(isUpdateNamesSuccessful = false)
        }
    }

    private fun updateNames() {
        _personalInformation.update { it.copy(isLoading = true, errorMessage = UiMessage.NONE) }
        viewModelScope.launch {
            updateNamesUseCase.invoke(_personalInformation.value.firstName, _personalInformation.value.lastName).fold(
                onSuccess = { user ->
                    Log.d("TAG", "Update names request successful")
                    _personalInformation.update {
                        it.copy(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            email = user.email,
                            isUpdateNamesSuccessful = true,
                            isLoading = false,
                            errorMessage = UiMessage.NONE
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("TAG", "Update names request failed: ${error.message}")
                    _personalInformation.update {
                        it.copy(
                            isLoading = false,
                            isUpdateNamesSuccessful = false,
                            errorMessage = error.toUiMessageRes()
                        )
                    }
                }
            )
        }
    }

    fun validateNames(){
        _personalInformation.update { it.copy(errorMessage = UiMessage.NONE) }
        val firstNameValid = isNameValid(_personalInformation.value.firstName)
        val lastNameValid = isNameValid(_personalInformation.value.lastName)
        if(firstNameValid == SignupNamesErrors.NONE &&
            lastNameValid == SignupNamesErrors.NONE){
            _personalInformation.update {
                it.copy(isFirstNameError = SignupNamesErrors.NONE, isLastNameError = SignupNamesErrors.NONE)
            }
            updateNames()
        } else{
            _personalInformation.update {
                it.copy(isFirstNameError = firstNameValid, isLastNameError = lastNameValid)
            }
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            _personalInformation.update { it.copy(errorMessage = UiMessage.NONE) }
            getUserUseCase.invoke().fold(
                onSuccess = { user ->
                    _personalInformation.update {
                        it.copy(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            email = user.email,
                            errorMessage = UiMessage.NONE
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("TAG", "Get user failed: ${error.message}")
                    _personalInformation.update { it.copy(errorMessage = error.toUiMessageRes()) }
                }
            )
        }
    }
}