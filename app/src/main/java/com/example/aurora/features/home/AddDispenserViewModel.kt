package com.example.aurora.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.R
import com.example.aurora.data.error.toUiMessageRes
import com.example.aurora.ui.UiMessage
import com.example.aurora.domain.usecase.AddDispenserUseCase
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddDispenserViewModel(
    private val addDispenserUseCase: AddDispenserUseCase,
    private val listAllUserDispensersUseCase: ListAllUserDispensersUseCase
    ): ViewModel() {
    private val _dispenser = MutableStateFlow(DispenserData())
    val dispenser = _dispenser.asStateFlow()

    fun id(text: String) {
        _dispenser.update{
            it.copy(id = text, errorMessage = UiMessage.NONE)
        }
    }

    fun name(text: String) {
        _dispenser.update{
            it.copy(name = text, errorMessage = UiMessage.NONE)
        }
    }

    fun fetchDispenserNames(){
        viewModelScope.launch {
            listAllUserDispensersUseCase().fold(
                onSuccess = { data ->
                    val names = data.dispensers.map { it.name }
                    val ids = data.dispensers.map { it.serial_id }
                    _dispenser.update {
                        it.copy(
                            allDispenserNames = names,
                            allDispenserIds = ids,
                            errorMessage = UiMessage.NONE,
                            //isCountError = names.size >= 5
                        )
                    }
                    Log.d("TAG", "ids ${_dispenser.value.allDispenserIds}")
                    Log.d("TAG", "names ${_dispenser.value.allDispenserNames}")
                },
                onFailure = { error ->
                    _dispenser.update { it.copy(errorMessage = error.toUiMessageRes()) }
                }
            )
        }
    }

    private fun addDispenser(){
        _dispenser.update { it.copy(isLoading = true, errorMessage = UiMessage.NONE) }
        viewModelScope.launch{
            addDispenserUseCase.invoke(_dispenser.value.id, _dispenser.value.name).fold(
                onSuccess = {
                    Log.d("TAG", "add dispenser request")
                    _dispenser.update {
                        it.copy(isAddDispenserSuccessful = true, isLoading = false, errorMessage = UiMessage.NONE)
                    }
                },
                onFailure = { error ->
                    _dispenser.update { it.copy(isLoading = false, errorMessage = error.toUiMessageRes()) }
                }
            )
        }
    }

    fun resetAdd() {
        _dispenser.update {
            it.copy(isAddDispenserSuccessful = false)
        }
    }

    private fun isIDValid(): AddDispenserIDErrors {
        return if( _dispenser.value.id.isEmpty()){
            AddDispenserIDErrors.EMPTY_ID
        } else if (!Regex("^[A-Z0-9]+-\\d{8}-\\d{4}$")
            .matches(_dispenser.value.id)){
            AddDispenserIDErrors.INVALID_ID
        } else if(_dispenser.value.allDispenserIds
                .any { it == _dispenser.value.id }){
            AddDispenserIDErrors.EXISTING_DISPENSER
        } else{
            AddDispenserIDErrors.NONE
        }
    }

    private fun isNameValid(): AddDispenserNameErrors {
        return if( _dispenser.value.name.isEmpty()){
            AddDispenserNameErrors.EMPTY_NAME
        } else if(!Regex("^[\\p{L}\\p{N} _-]{3,}$")
                .matches(_dispenser.value.name)){
            AddDispenserNameErrors.INVALID_NAME
        } else if(_dispenser.value.allDispenserNames
                .any { it == _dispenser.value.name }){
            AddDispenserNameErrors.REPEATING_NAME
        } else{
            AddDispenserNameErrors.NONE
        }
    }

    fun validate(){
        _dispenser.update { it.copy(errorMessage = UiMessage.NONE) }
        val idValid = isIDValid()
        val nameValid = isNameValid()
        if(idValid == AddDispenserIDErrors.NONE && nameValid == AddDispenserNameErrors.NONE /*&& !_dispenser.value.isCountError*/){
            _dispenser.update {
                it.copy(isIDError = AddDispenserIDErrors.NONE, isNameError = AddDispenserNameErrors.NONE)
            }
            addDispenser()
        } else{
            _dispenser.update {
                it.copy(isIDError = idValid, isNameError = nameValid)
            }
        }
    }
}

enum class AddDispenserIDErrors(val value: Int? = null){
    EMPTY_ID(R.string.error_empty_id),
    INVALID_ID(R.string.error_wrong_id),
    EXISTING_DISPENSER(R.string.error_existing_dispenser),
    NONE
}

enum class AddDispenserNameErrors(val value: Int? = null){
    EMPTY_NAME(R.string.error_empty_name),
    INVALID_NAME(R.string.error_wrong_name),
    REPEATING_NAME(R.string.error_repeating_name),
    NONE
}