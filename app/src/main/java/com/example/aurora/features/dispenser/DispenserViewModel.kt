package com.example.aurora.features.dispenser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.domain.usecase.DeleteDispenserUseCase
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
import com.example.aurora.domain.usecase.UpdateDispenserNameUseCase
import com.example.aurora.features.home.AddDispenserNameErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DispenserViewModel(
    private val deleteDispenserUseCase: DeleteDispenserUseCase,
    private val listAllUserDispensersUseCase: ListAllUserDispensersUseCase,
    private val updateDispenserNameUseCase: UpdateDispenserNameUseCase
): ViewModel() {

    private val _dispenser = MutableStateFlow(DispenserData())
    val dispenser = _dispenser.asStateFlow()

    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()

    private val _showPopUpRename = MutableStateFlow(false)
    val showPopUpRename = _showPopUpRename.asStateFlow()

    fun showHideRenameBack() {
        _showPopUpRename.update { value -> value.not() }
    }

    fun showHideDeleteBack() {
        _showPopUpDelete.update { value -> value.not() }
    }

    private fun setBaseInfo(
        name: String,
        id: String,
        containers: List<ContainerItem> = emptyList(),
        allNames: List<String> = emptyList(),
    ) {
        val normalizedContainers = containers.mapIndexed { index, container ->
            container.copy(
                title = container.title.ifBlank { "Container ${index + 1}" }
            )
        }
        _dispenser.update {
            it.copy(
                name = name.ifBlank { "Unnamed dispenser" },
                id = id.ifBlank { "ID unavailable" },
                containers = normalizedContainers,
                renameDraft = name.ifBlank { "Unnamed dispenser" },
                allDispenserNames = allNames,
                isRenameError = AddDispenserNameErrors.NONE,
                errorMessage = null
            )
        }
    }

    fun deleteDispenser(name: String, onSuccess: () -> Unit) {
        _showPopUpDelete.update { value -> value.not() }

        viewModelScope.launch {
            _dispenser.update { it.copy(errorMessage = null) }
            deleteDispenserUseCase(name).fold(
                onSuccess = {
                    Log.d("TAG", "Delete dispenser request successful")
                    onSuccess()
                },
                onFailure = { error ->
                    Log.e("TAG", "Delete dispenser request failed: ${error.message}")
                    _dispenser.update {
                        it.copy(
                            errorMessage = error.message ?: "Unable to delete dispenser"
                        )
                    }
                }
            )
        }
    }

    fun loadDispenser(dispenserId: String, fallbackName: String) {
        viewModelScope.launch {
            _dispenser.update { it.copy(errorMessage = null) }
            listAllUserDispensersUseCase().fold(
                onSuccess = { data ->
                    val dispenser = data.dispensers.firstOrNull { it.id.toString() == dispenserId }
                    val allNames = data.dispensers.map { it.name }
                    if (dispenser != null) {
                        val containers = dispenser.containers.mapIndexed { index, container ->
                            ContainerItem(
                                title = container.pillName.ifBlank { "Container ${index + 1}" },
                                subtitle = "Slot ${container.slotNumber}",
                                slotNumber = container.slotNumber,
                            )
                        }
                        setBaseInfo(
                            name = dispenser.name,
                            id = dispenser.id.toString(),
                            containers = containers,
                            allNames = allNames
                        )
                    } else {
                        _dispenser.update {
                            it.copy(
                                name = fallbackName.ifBlank { "Unnamed dispenser" },
                                id = dispenserId.ifBlank { "ID unavailable" },
                                containers = emptyList(),
                                allDispenserNames = allNames,
                                errorMessage = "Dispenser not found"
                            )
                        }
                    }
                },
                onFailure = { error ->
                    _dispenser.update {
                        it.copy(
                            name = fallbackName.ifBlank { "Unnamed dispenser" },
                            id = dispenserId.ifBlank { "ID unavailable" },
                            containers = emptyList(),
                            errorMessage = error.message ?: "Unable to load dispenser"
                        )
                    }
                }
            )
        }
    }

    fun setRenameDraft(text: String) {
        _dispenser.update {
            it.copy(renameDraft = text)
        }
    }

    private fun isNameValid(name: String): AddDispenserNameErrors {
        return if(name.isEmpty()){
            AddDispenserNameErrors.EMPTY_NAME
        } else if (!Regex("^[\\p{L}\\p{N} _-]{3,}$")
                .matches(name)){
            AddDispenserNameErrors.INVALID_NAME
        } else if(_dispenser.value.allDispenserNames
                .any { it == name && it != _dispenser.value.name }){
            return AddDispenserNameErrors.REPEATING_NAME
        } else{
            AddDispenserNameErrors.NONE
        }
    }

    fun confirmRename() {
        val nameValid = isNameValid(_dispenser.value.renameDraft)
        if (nameValid == AddDispenserNameErrors.NONE) {
            _dispenser.update {
                it.copy(isRenameError = nameValid)
            }
            rename()
        } else {
            _dispenser.update {
                it.copy(isRenameError = nameValid)
            }
        }
    }

    private fun rename(){
        _showPopUpRename.update { value -> value.not() }

        viewModelScope.launch {
            updateDispenserNameUseCase.invoke(_dispenser.value.name, _dispenser.value.renameDraft).fold(
                onSuccess = { dispenser ->
                    _dispenser.update {
                        it.copy(
                            name = dispenser.name,
                            renameDraft = dispenser.name,
                            allDispenserNames = _dispenser.value.allDispenserNames
                                .map{ dispenser.name },
                            isRenameSuccessful = true,
                        )
                    }
                },
                onFailure = { error ->
                    _dispenser.update {
                        it.copy(errorMessage = error.toString())
                    }
                }
            )
        }
    }

    fun resetRename(){
        _dispenser.update {
            it.copy(isRenameSuccessful = false)
        }
    }

}

