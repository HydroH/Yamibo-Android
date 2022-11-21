package com.hydroh.yamibo.ui.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydroh.yamibo.data.DataProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    var uiState by mutableStateOf(LoginUIState())
        private set

    fun updateUsername(username: String) {
        uiState = uiState.copy(username = username)
    }

    fun updatePassword(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun toggleShowPassword() {
        uiState = uiState.copy(showPassword = !uiState.showPassword)
    }

    fun submitLogin() {
        uiState = uiState.copy(
            loginState = LoginState.LOADING,
            exception = null,
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                DataProvider.login(uiState.username, uiState.password)
                uiState = uiState.copy(loginState = LoginState.SUCCESS)
            } catch (e: Exception) {
                uiState = uiState.copy(
                    loginState = LoginState.FAIL,
                    exception = e,
                )
            }
        }
    }
}

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val loginState: LoginState = LoginState.BEFORE,
    val exception: Exception? = null,
)

enum class LoginState {
    BEFORE,
    LOADING,
    SUCCESS,
    FAIL,
}