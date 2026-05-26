package com.example.aurora.features.login

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aurora.R
import com.example.aurora.data.error.toUiMessageRes
import com.example.aurora.ui.UiMessage
import com.example.aurora.data.repository.TokenStorage
import com.example.aurora.domain.usecase.LoginUseCase
import com.example.aurora.domain.usecase.RegisterPushTokenUseCase
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerPushTokenUseCase: RegisterPushTokenUseCase,
    private val tokenStorage: TokenStorage,
) : ViewModel() {
    private val _login = MutableStateFlow(LoginData())

    val login = _login.asStateFlow()

    fun email(text: String) {
        _login.update{
            it.copy(email = text, errorMessage = UiMessage.NONE)
        }
    }

    fun password(text: String) {
        _login.update{
            it.copy(password = text, errorMessage = UiMessage.NONE)
        }
    }

    fun login() {
        _login.update { it.copy(isLoading = true, errorMessage = UiMessage.NONE) }
        viewModelScope.launch {
            loginUseCase.invoke(_login.value.email, _login.value.password).fold(
                onSuccess = { result ->
                    registerPushTokenIfAvailable()
                    _login.update {
                        it.copy(
                            isLoginSuccessful = true,
                            firstName = result.firstName,
                            lastName = result.lastName,
                            isAdmin = result.isSuperuser,
                            isLoading = false,
                            errorMessage = UiMessage.NONE,
                        )
                    }
                },
                onFailure = { error ->
                    _login.update {
                        it.copy(
                            isLoginSuccessful = false,
                            isLoading = false,
                            errorMessage = error.toUiMessageRes(),
                        )
                    }
                }
            )
        }
    }

    fun resetLogin(){
        _login.update {
            it.copy(isLoginSuccessful = false)
        }
    }

    private fun isPasswordValid(): LoginPasswordErrors {
     return if(_login.value.password.isEmpty()){
         LoginPasswordErrors.EMPTY_PASSWORD
      } else if(!Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z\\d])[A-Za-z\\d\\p{Punct}]{8,}$")
             .matches(_login.value.password)){
         LoginPasswordErrors.INVALID_PASSWORD
      } else{
          LoginPasswordErrors.NONE
      }
    }

    private fun isEmailValid(): LoginEmailErrors{
        return if( _login.value.email.isEmpty()){
            LoginEmailErrors.EMPTY_EMAIL
        } else if (!Patterns.EMAIL_ADDRESS.matcher(_login.value.email).matches()){
            LoginEmailErrors.INVALID_EMAIL
        } else{
            LoginEmailErrors.NONE
        }
    }

    fun validate(){
        val emailValid = isEmailValid()
        val passwordValid = isPasswordValid()
        if( emailValid == LoginEmailErrors.NONE && passwordValid == LoginPasswordErrors.NONE){
            _login.update {
                it.copy(isEmailError = LoginEmailErrors.NONE, isPasswordError = LoginPasswordErrors.NONE)
            }
            login()
        } else{
            _login.update {
                it.copy(isEmailError = emailValid, isPasswordError = passwordValid)
            }
        }
    }

    private fun registerPushTokenIfAvailable() {
        val cachedToken = tokenStorage.getPushToken()
        if (!cachedToken.isNullOrBlank()) {
            viewModelScope.launch {
                registerPushTokenUseCase(cachedToken).onFailure {
                    Log.e("AuroraFCM", "Cached push token register failed: ${it.message}")
                }
            }
            return
        }

        try {
            FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("AuroraFCM", "FCM token fetch on login failed", task.exception)
                    return@addOnCompleteListener
                }
                val token = task.result ?: return@addOnCompleteListener
                tokenStorage.savePushToken(token)
                viewModelScope.launch {
                    registerPushTokenUseCase(token).onFailure {
                        Log.e("AuroraFCM", "Fresh push token register failed: ${it.message}")
                    }
                }
            }
        } catch (error: Throwable) {
            Log.w("AuroraFCM", "Firebase unavailable; push registration skipped", error)
        }
    }
}

enum class LoginEmailErrors(val value: Int? = null){
    EMPTY_EMAIL(R.string.error_empty_email),
    INVALID_EMAIL(R.string.error_wrong_email),
    NONE
}

enum class LoginPasswordErrors(val value: Int? = null){
    EMPTY_PASSWORD(R.string.error_empty_password),
    INVALID_PASSWORD(R.string.error_wrong_password),
    NOT_MATCHING(R.string.error_mismatch_passwords),
    NONE
}
