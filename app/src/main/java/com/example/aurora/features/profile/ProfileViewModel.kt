package com.example.aurora.features.profile

import androidx.lifecycle.ViewModel
import com.example.aurora.data.model.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ProfileViewModel(/*userModel: UserModel*/): ViewModel() {
    private val _showPopUpLogOut = MutableStateFlow(false)
    val showPopUpLogOut = _showPopUpLogOut.asStateFlow()
    private val _showPopUpDelete = MutableStateFlow(false)
    val showPopUpDelete = _showPopUpDelete.asStateFlow()
    //private val _personalInformation = userModel

    fun showHideLogOutBack() {
        _showPopUpLogOut.update { value -> value.not()  }
    }

    fun showHideDeleteBack() {
        _showPopUpDelete.update { value -> value.not()  }
    }

    fun showHideLogOut(){
        _showPopUpLogOut.update { value -> value.not()  }
        // delete tokens, send to log in page, backend log out
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