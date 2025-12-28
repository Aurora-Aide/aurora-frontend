package com.example.aurora.features.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.R
import com.example.aurora.domain.usecase.AddDispenserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddDispenserViewModel(private val addDispenserUseCase: AddDispenserUseCase): ViewModel() {
    private val _dispenser = MutableStateFlow(DispenserData())

    val dispenser = _dispenser.asStateFlow()

    fun id(text: String) {
        _dispenser.update{
            it.copy(id = text)
        }
    }

    fun name(text: String) {
        _dispenser.update{
            it.copy(name = text)
        }
    }

    private fun addDispenser(){
        viewModelScope.launch{
            addDispenserUseCase.invoke(_dispenser.value.id, _dispenser.value.name, "").fold(
                onSuccess = {
                    Log.d("TAG", "add dispenser request")
                    _dispenser.update {
                        it.copy(isAddDispenserSuccessful = true)
                    }
                },
                onFailure = {

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
        } else if (!Regex("^[A-Za-z][–-](\\d{4})(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])[–-]\\d{4}$")
            .matches(_dispenser.value.id)){
            AddDispenserIDErrors.INVALID_ID
        } else if(_dispenser.value.id == "") {     // TODO check if this dispenser has already been added
            AddDispenserIDErrors.EXISTING_DISPENSER
        } else{
            AddDispenserIDErrors.NONE
        }
    }

    private fun isNameValid(): AddDispenserNameErrors {
        return if( _dispenser.value.name.isEmpty()){
            AddDispenserNameErrors.EMPTY_NAME
        } else if (!Regex("^[\\p{L}\\p{N} _-]{3,}$")
                .matches(_dispenser.value.name)){
            AddDispenserNameErrors.INVALID_NAME
        } else if(_dispenser.value.name == ""){   // TODO check for other names
            AddDispenserNameErrors.REPEATING_NAME
        } else{
            AddDispenserNameErrors.NONE
        }
    }

    fun validate(){
        val idValid = isIDValid()
        val nameValid = isNameValid()
        if( idValid == AddDispenserIDErrors.NONE && nameValid == AddDispenserNameErrors.NONE){
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