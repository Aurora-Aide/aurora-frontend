package com.example.aurora.features.dispenser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.error.toUiMessage
import com.example.aurora.domain.usecase.DispenseNowUseCase
import com.example.aurora.domain.usecase.ListSchedulesUseCase
import com.example.aurora.domain.usecase.UpdatePillNameUseCase
import com.example.aurora.features.home.AddDispenserNameErrors
import com.example.aurora.features.dispenser.DaysOfWeek.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContainerViewModel(
    private val updatePillNameUseCase: UpdatePillNameUseCase,
    private val listSchedulesUseCase: ListSchedulesUseCase,
    private val dispenseNowUseCase: DispenseNowUseCase
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
        pillName: String,
        containerId: Int
    ) {
        _container.update {
            // Avoid overwriting a renamed pill when coming back from another screen
            val keepExistingName = it.containerId == containerId && it.pillName.isNotBlank()
            it.copy(
                dispenserName = dispenserName,
                slotNumber = slotNumber,
                pillName = if (keepExistingName) it.pillName else pillName,
                renameDraft = if (keepExistingName) it.renameDraft else pillName,
                containerId = containerId
            )
        }
    }

    fun setRenameDraft(text: String) {
        _container.update {
            it.copy(renameDraft = text, errorMessage = "")
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
        _container.update { it.copy(errorMessage = "") }
        val nameValid = isNameValid(_container.value.renameDraft)
        if (nameValid == AddDispenserNameErrors.NONE) {
            _container.update {
                it.copy(isRenameError = nameValid, pillName = _container.value.renameDraft)
            }
            updatePillName()
        } else {
            _container.update {
                it.copy(isRenameError = nameValid)
            }
        }
    }

    private fun mapSchedule(model: com.example.aurora.data.entity.ScheduleEntity): ScheduleData {
        val day = when (model.dayOfWeek) {
            0 -> MONDAY
            1 -> TUESDAY
            2 -> WEDNESDAY
            3 -> THURSDAY
            4 -> FRIDAY
            5 -> SATURDAY
            6 -> SUNDAY
            else -> DaysOfWeek.EMPTY
        }
        return ScheduleData(
            id = model.id,
            day = day,
            hour = model.hour,
            minutes = model.minute,
            repeating = model.repeat
        )
    }

    fun listSchedules() {
        viewModelScope.launch {
            _container.update { it.copy(errorMessage = "") }
            listSchedulesUseCase(_container.value.containerId).fold(
                onSuccess = { schedules ->
                    _container.update {
                        it.copy(
                            schedules = schedules.map { schedule -> mapSchedule(schedule) },
                            errorMessage = ""
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("TAG", "List schedules failed: ${error.message}")
                    _container.update { it.copy(errorMessage = error.toUiMessage()) }
                }
            )
        }
    }

    fun dispenseNow() {
        val currentContainerId = _container.value.containerId
        if (currentContainerId == 0) {
            _container.update { it.copy(errorMessage = "Container is not loaded yet.") }
            return
        }

        viewModelScope.launch {
            _container.update { it.copy(errorMessage = "") }
            dispenseNowUseCase(currentContainerId).fold(
                onSuccess = {
                    listSchedules()
                },
                onFailure = { error ->
                    Log.e("TAG", "Dispense now failed: ${error.message}")
                    _container.update { it.copy(errorMessage = error.toUiMessage()) }
                }
            )
        }
    }

    private fun updatePillName() {
        _showPopUpRename.update { value -> value.not() }

        viewModelScope.launch {
            _container.update { it.copy(errorMessage = "") }
            updatePillNameUseCase(_container.value.dispenserName,
                _container.value.slotNumber, _container.value.renameDraft).fold(
                onSuccess = { container ->
                    Log.d("TAG", "Update pill name successful")
                    _container.update {
                        it.copy(
                            pillName = container.pillName,
                            renameDraft = container.pillName,
                            isUpdating = false,
                            errorMessage = ""
                        )
                    }
                },
                onFailure = { error ->
                    Log.e("TAG", "Update pill name failed: ${error.message}")
                    _container.update {
                        it.copy(
                            isUpdating = false,
                            errorMessage = error.toUiMessage()
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