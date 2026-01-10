package com.example.aurora.features.dispenser

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.model.UpdateScheduleRequest
import com.example.aurora.domain.usecase.GetScheduleUseCase
import com.example.aurora.domain.usecase.UpdateScheduleUseCase
import com.example.aurora.domain.usecase.DeleteScheduleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val getScheduleUseCase: GetScheduleUseCase,
    private val updateScheduleUseCase: UpdateScheduleUseCase,
    private val deleteScheduleUseCase: DeleteScheduleUseCase
): ViewModel() {

    private val _schedule = MutableStateFlow(ScheduleDetailData())
    val schedule = _schedule.asStateFlow()

    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()

    private var originalDay: Int = -1
    private var originalHour: Int = -1
    private var originalMinute: Int = -1
    private var originalRepeat: Boolean = false

    fun showHideDelete() {
        _showPopUpDelete.update { value -> value.not() }
    }

    fun load(scheduleId: Int, containerName: String, dispenserName: String) {
        viewModelScope.launch {
            _schedule.update {
                it.copy(
                    isLoading = true,
                    errorMessage = null,
                    isDeleted = false,
                    isSuccess = false,
                    id = scheduleId,
                    containerName = containerName,
                    dispenserName = dispenserName
                )
            }
            getScheduleUseCase(scheduleId).fold(
                onSuccess = { schedule ->
                    originalDay = schedule.dayOfWeek
                    originalHour = schedule.hour
                    originalMinute = schedule.minute
                    originalRepeat = schedule.repeat
                    _schedule.update {
                        it.copy(
                            dayOfWeek = schedule.dayOfWeek,
                            hour = schedule.hour,
                            minute = schedule.minute,
                            repeat = schedule.repeat,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                },
                onFailure = { error ->
                    _schedule.update { it.copy(isLoading = false, errorMessage = error.message ?: "Unable to load schedule") }
                }
            )
        }
    }

    fun toggleEdit() {
        val currentlyEditing = _schedule.value.isEditing
        if (currentlyEditing) {
            // cancel edit: restore original values
            _schedule.update {
                it.copy(
                    isEditing = false,
                    errorMessage = null,
                    dayOfWeek = originalDay,
                    hour = originalHour,
                    minute = originalMinute,
                    repeat = originalRepeat
                )
            }
        } else {
            _schedule.update { it.copy(isEditing = true, errorMessage = null) }
        }
    }

    fun onDayChange(day: Int) {
        _schedule.update {
            it.copy(dayOfWeek = day, errorMessage = null)
        }
    }

    fun onHourChange(hour: Int) {
        _schedule.update {
            it.copy(hour = hour, errorMessage = null)
        }
    }

    fun onMinuteChange(minute: Int) {
        _schedule.update {
            it.copy(minute = minute, errorMessage = null)
        }
    }

    fun onRepeatChange(repeat: Boolean) {
        _schedule.update {
            it.copy(repeat = repeat)
        }
    }

    private fun validate(): Boolean {
        val dayValid = _schedule.value.dayOfWeek in 0..6
        val hourValid = _schedule.value.hour in 0..23
        val minuteValid = _schedule.value.minute in 0..59
        val valid = dayValid && hourValid && minuteValid
        if (!valid) {
            _schedule.update { it.copy(errorMessage = "Please select a valid day/time") }
        }
        return valid
    }

    fun save() {
        if (!validate()) return
        viewModelScope.launch {
            _schedule.update { it.copy(isSaving = true, errorMessage = null) }
            updateScheduleUseCase(
                _schedule.value.id,
                UpdateScheduleRequest(
                    dayOfWeek = _schedule.value.dayOfWeek,
                    hour = _schedule.value.hour,
                    minute = _schedule.value.minute,
                    repeat = _schedule.value.repeat
                )
            ).fold(
                onSuccess = {
                    originalDay = _schedule.value.dayOfWeek
                    originalHour = _schedule.value.hour
                    originalMinute = _schedule.value.minute
                    originalRepeat = _schedule.value.repeat
                    _schedule.update { it.copy(isSaving = false, isEditing = false, isSuccess = true) }
                },
                onFailure = { error ->
                    _schedule.update { it.copy(isSaving = false, errorMessage = error.message ?: "Unable to save") }
                }
            )
        }
    }

    fun deleteSchedule() {
        _showPopUpDelete.update { value -> value.not() }

        viewModelScope.launch {
            _schedule.update { it.copy(errorMessage = null) }
            deleteScheduleUseCase(_schedule.value.id).fold(
                onSuccess = {
                    Log.d("TAG", "Delete schedule request successful")
                    _schedule.update { it.copy(isDeleted = true) }
                },
                onFailure = { error ->
                    Log.d("TAG", "Delete schedule request NOT successful")
                    _schedule.update { it.copy(errorMessage = error.message ?: "Unable to delete") }
                }
            )
        }
    }

    fun resetSuccess() {
        _schedule.update { it.copy(isSuccess = false, isDeleted = false) }
    }
}

