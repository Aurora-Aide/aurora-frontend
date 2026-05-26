package com.example.aurora.features.dispenser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.error.toUiMessageRes
import com.example.aurora.ui.UiMessage
import com.example.aurora.domain.usecase.DeleteDispenserUseCase
import com.example.aurora.domain.usecase.GetDispenserUseCase
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
import com.example.aurora.domain.usecase.ResetDispenserPairingUseCase
import com.example.aurora.domain.usecase.UpdateDispenserNameUseCase
import com.example.aurora.features.home.AddDispenserNameErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DispenserViewModel(
    private val deleteDispenserUseCase: DeleteDispenserUseCase,
    private val getDispenserUseCase: GetDispenserUseCase,
    private val listAllUserDispensersUseCase: ListAllUserDispensersUseCase,
    private val updateDispenserNameUseCase: UpdateDispenserNameUseCase,
    private val resetDispenserPairingUseCase: ResetDispenserPairingUseCase
): ViewModel() {

    private val _dispenser = MutableStateFlow(DispenserData())
    val dispenser = _dispenser.asStateFlow()

    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()

    private val _showPopUpRename = MutableStateFlow(false)
    val showPopUpRename = _showPopUpRename.asStateFlow()

    private val _showPopUpResetPairing = MutableStateFlow(false)
    val showPopUpResetPairing = _showPopUpResetPairing.asStateFlow()

    fun showHideRenameBack() {
        _showPopUpRename.update { value -> value.not() }
    }

    fun showHideDeleteBack() {
        _showPopUpDelete.update { value -> value.not() }
    }

    fun showHideResetPairingBack() {
        _showPopUpResetPairing.update { value -> value.not() }
    }

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
        _dispenser.update {
            it.copy(
                name = name.ifBlank { "Unnamed dispenser" },
                id = id,
                containers = normalizedContainers,
                renameDraft = name.ifBlank { "Unnamed dispenser" },
                isRenameError = AddDispenserNameErrors.NONE,
                errorMessage = UiMessage.NONE,
                statusMessage = ""
            )
        }
    }

    private fun fetchDispenserNames(){
        viewModelScope.launch {
            _dispenser.update { it.copy(errorMessage = UiMessage.NONE) }
            listAllUserDispensersUseCase().fold(
                onSuccess = { data ->
                    val names = data.dispensers.map { it.name }
                    _dispenser.update { it.copy(allDispenserNames = names) }

                },
                onFailure = { error ->
                    _dispenser.update { it.copy(errorMessage = error.toUiMessageRes()) }
                }
            )
        }
    }

    fun deleteDispenser(name: String, onSuccess: () -> Unit) {
        _showPopUpDelete.update { value -> value.not() }

        viewModelScope.launch {
            _dispenser.update { it.copy(errorMessage = UiMessage.NONE) }
            deleteDispenserUseCase(name).fold(
                onSuccess = {
                    Log.d("TAG", "Delete dispenser request successful")
                    onSuccess()
                },
                onFailure = { error ->
                    Log.e("TAG", "Delete dispenser request failed: ${error.message}")
                    _dispenser.update {
                        it.copy(
                            errorMessage = error.toUiMessageRes()
                        )
                    }
                }
            )
        }
    }

    fun loadDispenser(dispenserId: String) {
        viewModelScope.launch {
            _dispenser.update { it.copy(errorMessage = UiMessage.NONE, statusMessage = "") }

            getDispenserUseCase(dispenserId).fold(
                onSuccess = { dispenser ->
                    val containers = dispenser.containers.mapIndexed { index, container ->
                        ContainerItem(
                            title = container.pillName.ifBlank { "Container ${index + 1}" },
                            subtitle = "Slot ${container.slotNumber}",
                            slotNumber = container.slotNumber,
                            containerId = container.id,
                        )
                    }
                    setBaseInfo(
                        name = dispenser.name,
                        id = dispenser.id,
                        containers = containers,
                    )
                    fetchDispenserNames()
                },
                onFailure = { error ->
                    _dispenser.update {
                        it.copy(
                            errorMessage = error.toUiMessageRes(),
                            statusMessage = "",
                            containers = emptyList()
                        )
                    }
                }
            )
        }
    }

    fun setRenameDraft(text: String) {
        _dispenser.update {
            it.copy(renameDraft = text, errorMessage = UiMessage.NONE)
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
            AddDispenserNameErrors.REPEATING_NAME
        } else{
            AddDispenserNameErrors.NONE
        }
    }

    fun confirmRename() {
        _dispenser.update { it.copy(errorMessage = UiMessage.NONE, statusMessage = "") }
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
            _dispenser.update { it.copy(errorMessage = UiMessage.NONE, statusMessage = "") }
            updateDispenserNameUseCase.invoke(_dispenser.value.name, _dispenser.value.renameDraft).fold(
                onSuccess = { dispenser ->
                    _dispenser.update {
                        it.copy(
                            name = dispenser.name,
                            renameDraft = dispenser.name,
                            allDispenserNames = _dispenser.value.allDispenserNames
                                .map{ dispenser.name },
                            isRenameSuccessful = true,
                            errorMessage = UiMessage.NONE
                        )
                    }
                },
                onFailure = { error ->
                    _dispenser.update {
                        it.copy(errorMessage = error.toUiMessageRes(), statusMessage = "")
                    }
                }
            )
        }
    }

    fun confirmResetPairing(onSuccess: (() -> Unit)? = null) {
        _showPopUpResetPairing.update { false }
        val dispenserId = _dispenser.value.id
        if (dispenserId.isBlank()) return

        viewModelScope.launch {
            _dispenser.update { it.copy(errorMessage = UiMessage.NONE, statusMessage = "") }
            resetDispenserPairingUseCase(dispenserId).fold(
                onSuccess = {
                    _dispenser.update {
                        it.copy(
                            errorMessage = UiMessage.NONE,
                            statusMessage = "Pairing reset. Reboot the dispenser to pair again."
                        )
                    }
                    onSuccess?.invoke()
                },
                onFailure = { error ->
                    _dispenser.update {
                        it.copy(errorMessage = error.toUiMessageRes(), statusMessage = "")
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