package com.example.aurora.features.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(): ViewModel() {
    private val _showPopUpLogOut = MutableStateFlow(false)
    val showPopUpLogOut = _showPopUpLogOut.asStateFlow()
    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()

    fun showHideLogOutBack() {
        _showPopUpLogOut.update { value -> value.not()  }
    }

    fun showHideDeleteBack() {
        _showPopUpDelete.update { value -> value.not()  }
    }

    fun showHideLogOut(){
        // delete tokens, send to log in page, backend log out
    }

    fun showHideDelete(){
        // delete tokens, send to sign up page, backend delete profile
    }
}