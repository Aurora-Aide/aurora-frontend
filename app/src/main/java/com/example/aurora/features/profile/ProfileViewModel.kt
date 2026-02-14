package com.example.aurora.features.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.error.toUiMessage
import com.example.aurora.domain.usecase.DeleteUserUseCase
import com.example.aurora.domain.usecase.GetUserUseCase
import com.example.aurora.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val logoutUseCase: LogoutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase
): ViewModel() {
    private val _showPopUpLogOut = MutableStateFlow(false)
    val showPopUpLogOut = _showPopUpLogOut.asStateFlow()
    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()
    private val _logout = MutableStateFlow(LogoutData())
    private val _personalInformation = MutableStateFlow(PersonalInformationData())
    val personalInfo = _personalInformation.asStateFlow()

    fun showHideLogOutBack() {
        _showPopUpLogOut.update { value -> value.not()  }
    }

    fun showHideDeleteBack() {
        _showPopUpDelete.update { value -> value.not()  }
    }

    fun performLogout(onSuccess: () -> Unit) {
        viewModelScope.launch {
            logoutUseCase.invoke().fold(
                onSuccess = {
                    _showPopUpLogOut.update { false }
                    Log.d("TAG", "log out request successful")
                    _logout.update {
                        it.copy(refresh = "", isLoggedOut = true, errorMessage = "")
                    }
                    onSuccess()
                },
                onFailure = { error ->
                    Log.e("TAG", "log out request failed: ${error.message}")
                    // Still hide popup on failure, but don't navigate
                    _showPopUpLogOut.update { false }
                    _logout.update { it.copy(errorMessage = error.toUiMessage()) }
                    _personalInformation.update { it.copy(errorMessage = error.toUiMessage()) }
                }
            )
        }
    }

    fun performDelete(onSuccess: () -> Unit) {
        viewModelScope.launch {
            deleteUserUseCase.invoke(_personalInformation.value.email).fold(
                onSuccess = {
                    Log.d("TAG", "Delete user request successful")
                    _logout.update {
                        it.copy(refresh = "", isLoggedOut = true, errorMessage = "")
                    }
                    _showPopUpDelete.update { false }
                    onSuccess()
                },
                onFailure = { error ->
                    Log.e("TAG", "Delete user request failed: ${error.message}")
                    // Still hide popup on failure, but don't navigate
                    _showPopUpDelete.update { false }
                    _logout.update { it.copy(errorMessage = error.toUiMessage()) }
                    _personalInformation.update { it.copy(errorMessage = error.toUiMessage()) }
                }
            )
        }
    }

    fun getUser(){
        viewModelScope.launch{
            getUserUseCase.invoke().fold(
                onSuccess = { user ->
                    Log.d("TAG", "Get user request")
                    _personalInformation.update {
                        it.copy(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            email = user.email,
                            isAdmin = user.isSuperuser,
                            errorMessage = ""
                        )
                    }
                },
                onFailure = { error ->
                    _personalInformation.update { it.copy(errorMessage = error.toUiMessage()) }
                }
            )
        }
    }
}
