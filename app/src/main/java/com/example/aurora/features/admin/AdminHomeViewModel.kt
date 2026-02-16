package com.example.aurora.features.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.error.toUiMessage
import com.example.aurora.domain.usecase.admin.AdminListDispensersUseCase
import com.example.aurora.domain.usecase.admin.AdminListDispenserModelsUseCase
import com.example.aurora.domain.usecase.admin.AdminListUsersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminHomeViewModel(
    private val listDispensersUseCase: AdminListDispensersUseCase,
    private val listModelsUseCase: AdminListDispenserModelsUseCase,
    private val listUsersUseCase: AdminListUsersUseCase
) : ViewModel() {

    private val _data = MutableStateFlow(AdminHomeData())
    val data = _data.asStateFlow()

    fun setName(name: String) {
        _data.update { it.copy(name = name) }
    }

    fun setTab(tab: AdminHomeTab) {
        _data.update { it.copy(activeTab = tab, errorMessage = "") }
        when (tab) {
            AdminHomeTab.DISPENSERS -> {
                if (_data.value.dispensers.isEmpty()) loadDispensers()
            }
            AdminHomeTab.MODELS -> {
                if (_data.value.models.isEmpty()) loadModels()
            }
            AdminHomeTab.USERS -> {
                if (_data.value.users.isEmpty()) loadUsers()
            }
        }
    }

    fun loadDispensers() {
        viewModelScope.launch {
            _data.update { it.copy(isLoading = true, errorMessage = "") }
            listDispensersUseCase().fold(
                onSuccess = { list ->
                    _data.update { it.copy(isLoading = false, dispensers = list, errorMessage = "") }
                },
                onFailure = { err ->
                    _data.update { it.copy(isLoading = false, errorMessage = err.toUiMessage()) }
                }
            )
        }
    }

    fun loadModels() {
        viewModelScope.launch {
            _data.update { it.copy(isLoading = true, errorMessage = "") }
            listModelsUseCase().fold(
                onSuccess = { list ->
                    _data.update { it.copy(isLoading = false, models = list, errorMessage = "") }
                },
                onFailure = { err ->
                    _data.update { it.copy(isLoading = false, errorMessage = err.toUiMessage()) }
                }
            )
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            _data.update { it.copy(isLoading = true, errorMessage = "") }
            listUsersUseCase().fold(
                onSuccess = { list ->
                    _data.update { it.copy(isLoading = false, users = list, errorMessage = "") }
                },
                onFailure = { err ->
                    _data.update { it.copy(isLoading = false, errorMessage = err.toUiMessage()) }
                }
            )
        }
    }
}
