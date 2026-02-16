package com.example.aurora.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.error.toUiMessage
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val listDispensersUseCase: ListAllUserDispensersUseCase
): ViewModel() {
    private val _dispensers = MutableStateFlow(DispensersData())
    val dispensers = _dispensers.asStateFlow()

    fun listDispensers() {
        viewModelScope.launch {
            _dispensers.update { it.copy(errorMessage = "") }
            listDispensersUseCase.invoke()
                .onSuccess { data ->
                    Log.d("TAG", "Home: List all user dispensers request")
                    _dispensers.update {
                        it.copy(dispensers = data.dispensers, errorMessage = "")
                    }
                }
                .onFailure { error ->
                    Log.e("TAG", "Home: Failed to load dispensers: ${error.message}")
                    _dispensers.update { it.copy(errorMessage = error.toUiMessage()) }
                }
        }
    }
}

