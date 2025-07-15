package com.example.djigit.features.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(): ViewModel() {
    private val _showPopUp = MutableStateFlow(false)
    val showPopUp = _showPopUp.asStateFlow()

    fun showHide() {
        _showPopUp.update { value -> value.not()  }
    }
}