package com.example.aurora.features.admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.R
import com.example.aurora.data.error.toUiMessage
import com.example.aurora.data.model.AdminCreateDispenserModelRequest
import com.example.aurora.domain.usecase.admin.AdminCreateDispenserModelUseCase
import com.example.aurora.domain.usecase.admin.AdminListDispenserModelsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AdminCreateDispenserModelViewModel(
    private val createModelUseCase: AdminCreateDispenserModelUseCase,
    private val listModelsUseCase: AdminListDispenserModelsUseCase
) : ViewModel() {

    private val _model = MutableStateFlow(AdminCreateDispenserModelData())
    val dispenseModel = _model.asStateFlow()

    fun onCodeChange(value: String){
        _model.update{
            it.copy(
                code = value, 
                errorMessage = "",
                isCodeError = AddModelCodeErrors.NONE
            )
        }
        
    }
    fun onNameChange(value: String) = _model.update {
        it.copy(name = value, errorMessage = "", isNameError = AddModelNameErrors.NONE)
    }
    fun onSlotCountChange(value: String) = _model.update {
        it.copy(slotCount = value, errorMessage = "", isSlotCountError = AddModelSlotCountErrors.NONE)
    }
    fun onSerialPrefixChange(value: String) = _model.update {
        it.copy(serialPrefix = value, errorMessage = "", isSerialPrefixError = AddModelSerialPrefixErrors.NONE)
    }

    private fun isCodeValid(): AddModelCodeErrors {
        return if( _model.value.code.isEmpty()){
            AddModelCodeErrors.EMPTY_CODE
        } else if (!Regex("^[A-Z0-9]{1,10}$")
                .matches(_model.value.code)){
            AddModelCodeErrors.INVALID_CODE
        } else if(_model.value.allModelsCodes
                .any { it == _model.value.code }){
            AddModelCodeErrors.REPEATING_CODE
        } else{
            AddModelCodeErrors.NONE
        }
    }

    private fun isNameValid(): AddModelNameErrors {
        return if( _model.value.name.isEmpty()){
            AddModelNameErrors.EMPTY_NAME
        } else if (_model.value.name.length >= 100){
            AddModelNameErrors.INVALID_NAME
        } else{
            AddModelNameErrors.NONE
        }
    }

    private fun isSerialPrefixValid(): AddModelSerialPrefixErrors {
        return if( _model.value.serialPrefix.isEmpty()){
            AddModelSerialPrefixErrors.EMPTY_PREFIX
        } else if (!Regex("^[A-Z0-9]{1,10}$")
                .matches(_model.value.serialPrefix)){
            AddModelSerialPrefixErrors.INVALID_PREFIX
        } else if(_model.value.allModelsPrefixes
                .any { it == _model.value.serialPrefix }){
            AddModelSerialPrefixErrors.REPEATING_PREFIX
        } else{
            AddModelSerialPrefixErrors.NONE
        }
    }

    private fun isSlotCountValid(): AddModelSlotCountErrors {
        return if( _model.value.slotCount.isEmpty()){
            AddModelSlotCountErrors.EMPTY_SLOT
        } else if (_model.value.slotCount.toIntOrNull() == null){
            AddModelSlotCountErrors.INVALID_SLOT
        } else if(_model.value.slotCount.toIntOrNull()!! <= 0){
            AddModelSlotCountErrors.NON_POSITIVE_SLOT
        } else{
            AddModelSlotCountErrors.NONE
        }
    }

    fun save() {
        _model.update { it.copy(errorMessage = "") }
        val codeError = isCodeValid()
        val nameError = isNameValid()
        val prefixError = isSerialPrefixValid()
        val slotError = isSlotCountValid()

        if(codeError == AddModelCodeErrors.NONE &&
            nameError == AddModelNameErrors.NONE &&
            prefixError == AddModelSerialPrefixErrors.NONE &&
            slotError == AddModelSlotCountErrors.NONE
        ){
            _model.update {
                it.copy(
                    isCodeError = codeError,
                    isNameError = nameError,
                    isSerialPrefixError = prefixError,
                    isSlotCountError = slotError
                )
            }
            createModel()
        } else{
            _model.update {
                it.copy(
                    isCodeError = codeError,
                    isNameError = nameError,
                    isSerialPrefixError = prefixError,
                    isSlotCountError = slotError
                )
            }
        }
        _model.update {
            it.copy(
                isCodeError = codeError,
                isNameError = nameError,
                isSerialPrefixError = prefixError,
                isSlotCountError = slotError
            )
        }
    }

    private fun createModel() {
        _model.update { it.copy(isLoading = true, errorMessage = "") }
        viewModelScope.launch {
            createModelUseCase(
                AdminCreateDispenserModelRequest(
                    code = _model.value.code,
                    name = _model.value.name,
                    slot_count = _model.value.slotCount,
                    serial_prefix = _model.value.serialPrefix
                )
            ).fold(
                onSuccess = {
                    _model.update { it.copy(isLoading = false, isSuccess = true, errorMessage = "") }
                },
                onFailure = { error ->
                    _model.update { it.copy(isLoading = false, errorMessage = error.toUiMessage()) }
                }
            )
        }
    }

    fun resetSuccess() {
        _model.update { it.copy(isSuccess = false) }
    }

    fun loadModels() {
        viewModelScope.launch {
            _model.update { it.copy(errorMessage = "") }
            listModelsUseCase().fold(
                onSuccess = { data ->
                    val codes = data.map { it.code }
                    val serialPrefix = data.map { it.serial_prefix }
                    _model.update { it.copy(allModelsCodes = codes, allModelsPrefixes = serialPrefix) }
                },
                onFailure = { error ->
                    _model.update { it.copy(errorMessage = error.toUiMessage()) }
                }
            )
        }
    }
}


enum class AddModelCodeErrors(val value: Int? = null){
    EMPTY_CODE(R.string.error_empty_code),
    INVALID_CODE(R.string.error_invalid_code),
    REPEATING_CODE(R.string.error_repeating_code),
    NONE
}

enum class AddModelNameErrors(val value: Int? = null){
    EMPTY_NAME(R.string.error_empty_name),
    INVALID_NAME(R.string.error_invalid_name),
    NONE
}


enum class AddModelSerialPrefixErrors(val value: Int? = null){
    EMPTY_PREFIX(R.string.error_empty_prefix),
    INVALID_PREFIX(R.string.error_invalid_prefix),
    REPEATING_PREFIX(R.string.error_repeating_prefix),
    NONE
}

enum class AddModelSlotCountErrors(val value: Int? = null) {
    EMPTY_SLOT(R.string.error_empty_slot),
    INVALID_SLOT(R.string.error_invalid_slot),
    NON_POSITIVE_SLOT(R.string.error_negative_slot),
    NONE
}