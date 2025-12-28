package com.example.aurora.features.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.domain.usecase.ListAllUserDispensersUseCase
import com.example.aurora.domain.usecase.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val logoutUseCase: LogoutUseCase, private val listDispensersUseCase: ListAllUserDispensersUseCase): ViewModel() {
    private val _showPopUpLogOut = MutableStateFlow(false)
    val showPopUpLogOut = _showPopUpLogOut.asStateFlow()
    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()
    private val _logout = MutableStateFlow(LogoutData())
    private val _dispenser = MutableStateFlow(DispenserData())
    //private val _personalInformation = userModel

    fun showHideLogOutBack() {
        _showPopUpLogOut.update { value -> value.not()  }
    }

    fun showHideDeleteBack() {
        _showPopUpDelete.update { value -> value.not()  }
    }

    fun showHideLogOut(){
        _showPopUpLogOut.update { value -> value.not() }

        viewModelScope.launch{
            logoutUseCase.invoke(_logout.value.refreshToken).fold(
                onSuccess = {
                    Log.d("TAG", "log out request")
                    _logout.update {
                        it.copy(refreshToken = null.toString())
                    }
                },
                onFailure = {

                }
            )
        }
    }

    fun showHideDelete(){
        _showPopUpDelete.update { value -> value.not()  }
        // delete tokens, send to sign up page, backend delete profile
    }

    fun listDispensers(){
        viewModelScope.launch{
            listDispensersUseCase.invoke(_logout.value.accessToken).fold(
                onSuccess = { data ->
                    Log.d("TAG", "List all user dispensers request")
                    _dispenser.update {
                        it.copy(id = data.id.toString(), name = data.name)
                    }
                },
                onFailure = {

                }
            )
        }
    }
}