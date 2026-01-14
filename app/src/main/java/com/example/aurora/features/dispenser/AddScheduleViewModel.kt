package com.example.aurora.features.dispenser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.model.CreateScheduleRequest
import com.example.aurora.domain.usecase.CreateScheduleUseCase
import com.example.aurora.domain.usecase.ListSchedulesUseCase
import com.example.aurora.data.entity.ScheduleEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddScheduleViewModel(
    private val createScheduleUseCase: CreateScheduleUseCase,
    private val listSchedulesUseCase: ListSchedulesUseCase
): ViewModel() {

    private val _schedule = MutableStateFlow(ScheduleFormState())
    val schedule = _schedule.asStateFlow()

    private var existingSchedules: List<ScheduleEntity> = emptyList()

    fun onDayChange(day: Int) {
        _schedule.update { it.copy(dayOfWeek = day, errorMessage = null) }
        updateValidity()
    }

    fun onHourChange(hour: Int) {
        _schedule.update { it.copy(hour = hour, errorMessage = null) }
        updateValidity()
    }

    fun onMinuteChange(minute: Int) {
        _schedule.update { it.copy(minute = minute, errorMessage = null) }
        updateValidity()
    }

    fun onRepeatChange(repeat: Boolean) {
        _schedule.update { it.copy(repeat = repeat) }
    }

    fun loadSchedules(containerId: Int) {
        viewModelScope.launch {
            listSchedulesUseCase(containerId).fold(
                onSuccess = { schedules ->
                    existingSchedules = schedules
                },
                onFailure = {
                    // Keep existing schedules; optionally surface a warning if needed
                }
            )
        }
    }

    private fun updateValidity() {
        val hourValid = _schedule.value.hour in 0..23
        val minuteValid = _schedule.value.minute in 0..59
        val dayValid = _schedule.value.dayOfWeek in 0..6
        val valid = hourValid && minuteValid && dayValid
        _schedule.update { it.copy(isValid = valid, errorMessage = if (valid) null else it.errorMessage) }
    }

    private fun validate(): Boolean {
        val hourValid = _schedule.value.hour in 0..23
        val minuteValid = _schedule.value.minute in 0..59
        val dayValid = _schedule.value.dayOfWeek in 0..6
        val baseValid = hourValid && minuteValid && dayValid
        if (!baseValid) {
            _schedule.update { it.copy(isValid = false, errorMessage = "Please select a valid day/time") }
            return false
        }

        val dup = existingSchedules.any {
            it.dayOfWeek == _schedule.value.dayOfWeek &&
            it.hour == _schedule.value.hour &&
            it.minute == _schedule.value.minute
        }
        if (dup) {
            _schedule.update { it.copy(isValid = false, errorMessage = "You already have this schedule!") }
            return false
        }

        _schedule.update { it.copy(isValid = true, errorMessage = null) }
        return true
    }

    fun save(containerId: Int) {
        if (!validate()) return
        viewModelScope.launch {
            _schedule.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }
            val req = CreateScheduleRequest(
                dayOfWeek = _schedule.value.dayOfWeek.coerceIn(0,6),
                hour = _schedule.value.hour.coerceIn(0,23),
                minute = _schedule.value.minute.coerceIn(0,59),
                repeat = _schedule.value.repeat
            )
            createScheduleUseCase(containerId, req).fold(
                onSuccess = {
                    _schedule.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { error ->
                    _schedule.update { it.copy(isLoading = false, errorMessage = error.message ?: "Unable to save schedule") }
                }
            )
        }
    }

    fun resetSuccess() {
        _schedule.update { it.copy(isSuccess = false) }
    }
}

