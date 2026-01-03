package com.example.aurora.features.dispenser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.domain.usecase.UpdatePillNameUseCase
import com.example.aurora.features.home.AddDispenserNameErrors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContainerViewModel(
    private val updatePillNameUseCase: UpdatePillNameUseCase
) : ViewModel() {

    private val _container = MutableStateFlow(ContainerData())
    val container = _container.asStateFlow()

    private val _showPopUpRename = MutableStateFlow(false)
    val showPopUpRename = _showPopUpRename.asStateFlow()

    fun showHideRenameBack() {
        _showPopUpRename.update { value -> value.not() }
    }

    fun setBaseInfo(
        dispenserName: String,
        slotNumber: Int,
        pillName: String
    ) {
        _container.update {
            it.copy(
                dispenserName = dispenserName,
                slotNumber = slotNumber,
                pillName = pillName,
                renameDraft = pillName
            )
        }
    }

    fun setRenameDraft(text: String) {
        _container.update {
            it.copy(renameDraft = text)
        }
    }

    private fun isNameValid(name: String): AddDispenserNameErrors {
        return if(name.isEmpty()){
            AddDispenserNameErrors.EMPTY_NAME
        } else if (!Regex("^[\\p{L}\\p{N} _-]{3,}$")
                .matches(name)){
            AddDispenserNameErrors.INVALID_NAME
        } else{
            AddDispenserNameErrors.NONE
        }
    }

    fun confirmRename() {
        val nameValid = isNameValid(_container.value.renameDraft)
        if (nameValid == AddDispenserNameErrors.NONE) {
            _container.update {
                it.copy(isRenameError = nameValid)
            }
            updatePillName()
        } else {
            _container.update {
                it.copy(isRenameError = nameValid)
            }
        }
    }

    private fun updatePillName() {
        _showPopUpRename.update { value -> value.not() }

        viewModelScope.launch {
            updatePillNameUseCase(_container.value.dispenserName, _container.value.slotNumber, _container.value.renameDraft).fold(
                onSuccess = { container ->
                    Log.d("TAG", "Update pill name successful")
                    _container.update {
                        it.copy(
                            pillName = container.pillName,
                            renameDraft = container.pillName,
                            isUpdating = false
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("TAG", "Update pill name failed: ${error.message}")
                    _container.update {
                        it.copy(
                            isUpdating = false,
                            errorMessage = error.message ?: "Unable to update pill name"
                        )
                    }
                }
            )
        }
    }

    fun resetRename(){
        _container.update {
            it.copy(isRenameSuccessful = false)
        }
    }
}