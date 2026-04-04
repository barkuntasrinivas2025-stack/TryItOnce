package com.TRY.tryitonce.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.TRY.tryitonce.domain.usecase.LoginUseCase
import com.TRY.tryitonce.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val loginSuccess: Boolean = false,
    val errorMessage: String? = null,
) {
    val isSubmitEnabled: Boolean
        get() = !isLoading && email.isNotBlank() && password.isNotBlank()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) =
        _uiState.update { it.copy(email = email, emailError = null, errorMessage = null) }

    fun onPasswordChange(password: String) =
        _uiState.update { it.copy(password = password, passwordError = null, errorMessage = null) }

    fun onErrorMessageShown() =
        _uiState.update { it.copy(errorMessage = null) }

    fun login() {
        val s = _uiState.value
        if (!s.isSubmitEnabled) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = loginUseCase(s.email.trim(), s.password)) {
                is Resource.Success -> _uiState.update { it.copy(isLoading = false, loginSuccess = true) }
                is Resource.Error -> {
                    val msg = result.message
                    _uiState.update {
                        it.copy(
                            isLoading     = false,
                            emailError    = msg.takeIf { it.contains("email", true) },
                            passwordError = msg.takeIf { it.contains("password", true) },
                            errorMessage  = msg.takeIf { !it.contains("email", true) && !it.contains("password", true) },
                        )
                    }
                }
                is Resource.Loading -> Unit
            }
        }
    }
}
