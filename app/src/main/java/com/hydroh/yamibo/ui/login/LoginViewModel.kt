package com.hydroh.yamibo.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hydroh.yamibo.data.DataProvider
import com.hydroh.yamibo.util.Consts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.apply {
            value = value.copy(username = username)
        }
    }

    fun updatePassword(password: String) {
        _uiState.apply {
            value = value.copy(password = password)
        }
    }

    fun toggleShowPassword() {
        _uiState.apply {
            value = value.copy(showPassword = !value.showPassword)
        }
    }

    fun submitLogin() {
        _uiState.apply {
            value = value.copy(
                loginState = LoginState.LOADING,
                errorMessage = "",
            )
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    DataProvider.login(value.username, value.password)
                    value = value.copy(loginState = LoginState.SUCCESS)
                } catch (e: Exception) {
                    value = value.copy(
                        loginState = LoginState.FAIL,
                        errorMessage = e.message ?: Consts.ERROR_UNKNOWN,
                    )
                }
            }
        }
    }
}

data class LoginUIState(
    val username: String = "",
    val password: String = "",
    val showPassword: Boolean = false,
    val loginState: LoginState = LoginState.BEFORE,
    val errorMessage: String = "",
)

enum class LoginState {
    BEFORE,
    LOADING,
    SUCCESS,
    FAIL,
}