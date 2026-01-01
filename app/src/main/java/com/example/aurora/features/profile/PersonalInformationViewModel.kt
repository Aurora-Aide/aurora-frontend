package com.example.aurora.features.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.domain.usecase.UpdateNamesUseCase
import com.example.aurora.domain.usecase.GetUserUseCase
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
            it.copy(firstName = text)
        }
    }

    fun lastName(text: String) {
        _personalInformation.update{
            it.copy(lastName = text)
        }
    }

    fun updateNames(onSuccess: () -> Unit) {
        viewModelScope.launch {
            updateNamesUseCase.invoke(_personalInformation.value.firstName, _personalInformation.value.lastName).fold(
                onSuccess = { user ->
                    Log.d("TAG", "Update names request successful")
                    _personalInformation.update {
                        it.copy(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            email = user.email
                        )
                    }
                    onSuccess()
                },
                onFailure = { error ->
                    Log.e("TAG", "Update names request failed: ${error.message}")
                }
            )
        }
    }

    private fun fetchUser() {
        viewModelScope.launch {
            getUserUseCase.invoke().fold(
                onSuccess = { user ->
                    _personalInformation.update {
                        it.copy(
                            firstName = user.firstName,
                            lastName = user.lastName,
                            email = user.email
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("TAG", "Get user failed: ${error.message}")
                }
            )
        }
    }
}