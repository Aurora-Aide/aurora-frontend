package com.example.aurora.features.dispenser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.domain.usecase.DeleteDispenserUseCase
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DispenserViewModel(
    private val deleteDispenserUseCase: DeleteDispenserUseCase,
    private val listAllUserDispensersUseCase: ListAllUserDispensersUseCase,
): ViewModel() {

    private val _uiState = MutableStateFlow(
        DispenserUiState(
            isLoading = true
        )
    )
    val uiState: StateFlow<DispenserUiState> = _uiState.asStateFlow()

    private fun setBaseInfo(
        name: String,
        id: String,
        containers: List<ContainerItem> = emptyList(),
    ) {
        val normalizedContainers = containers.mapIndexed { index, container ->
            container.copy(
                title = container.title.ifBlank { "Container ${index + 1}" }
            )
        }
        _uiState.update {
            it.copy(
                name = name.ifBlank { "Unnamed dispenser" },
                id = id.ifBlank { "ID unavailable" },
                containers = normalizedContainers,
                isLoading = false,
                errorMessage = null
            )
        }
    }

    fun deleteDispenser(name: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isDeleting = true, errorMessage = null) }
            deleteDispenserUseCase(name).fold(
                onSuccess = {
                    Log.d("TAG", "Delete dispenser request successful")
                    _uiState.update { state -> state.copy(isDeleting = false) }
                    onSuccess()
                },
                onFailure = { error ->
                    Log.e("TAG", "Delete dispenser request failed: ${error.message}")
                    _uiState.update {
                        it.copy(
                            isDeleting = false,
                            errorMessage = error.message ?: "Unable to delete dispenser"
                        )
                    }
                }
            )
        }
    }

    fun loadDispenser(dispenserId: String, fallbackName: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            listAllUserDispensersUseCase().fold(
                onSuccess = { data ->
                    val dispenser = data.dispensers.firstOrNull { it.id.toString() == dispenserId }
                    if (dispenser != null) {
                        val containers = dispenser.containers.mapIndexed { index, container ->
                            ContainerItem(
                                title = container.pillName.ifBlank { "Container ${index + 1}" },
                                subtitle = "Slot ${container.slotNumber}"
                            )
                        }
                        setBaseInfo(
                            name = dispenser.name,
                            id = dispenser.id.toString(),
                            containers = containers
                        )
                    } else {
                        _uiState.update {
                            it.copy(
                                name = fallbackName.ifBlank { "Unnamed dispenser" },
                                id = dispenserId.ifBlank { "ID unavailable" },
                                containers = emptyList(),
                                isLoading = false,
                                errorMessage = "Dispenser not found"
                            )
                        }
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            name = fallbackName.ifBlank { "Unnamed dispenser" },
                            id = dispenserId.ifBlank { "ID unavailable" },
                            containers = emptyList(),
                            isLoading = false,
                            errorMessage = error.message ?: "Unable to load dispenser"
                        )
                    }
                }
            )
        }
    }
}

data class DispenserUiState(
    val name: String = "",
    val id: String = "",
    val containers: List<ContainerItem> = emptyList(),
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val errorMessage: String? = null
)

data class ContainerItem(
    val title: String,
    val subtitle: String? = null,
)

