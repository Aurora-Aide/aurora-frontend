package com.example.aurora.features.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.data.model.UserModel
import com.example.aurora.domain.usecase.LogoutUseCase
import com.example.aurora.features.login.LoginData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val logoutUseCase: LogoutUseCase): ViewModel() {
    private val _showPopUpLogOut = MutableStateFlow(false)
    val showPopUpLogOut = _showPopUpLogOut.asStateFlow()
    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()
    private val _logout = MutableStateFlow(LogoutData())
    //private val _personalInformation = userModel

    fun showHideLogOutBack() {
        _showPopUpLogOut.update { value -> value.not()  }
    }

    fun showHideDeleteBack() {
        _showPopUpDelete.update { value -> value.not()  }
    }

    fun showHideLogOut(){
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
        _showPopUpLogOut.update { value -> value.not()  }
        // delete tokens, backend log out
    }

    fun showHideDelete(){
        _showPopUpDelete.update { value -> value.not()  }
        // delete tokens, send to sign up page, backend delete profile
    }

//    fun email(text: String) {
//        _personalInformation.update{
//            it.copy(email = text)
//        }
//    }
//
//
//    fun firstName(text: String) {
//        _personalInformation.update{
//            it.copy(firstName = text)
//        }
//    }
//
//    fun lastName(text: String) {
//        _personalInformation.update{
//            it.copy(lastName = text)
//        }
//    }
}