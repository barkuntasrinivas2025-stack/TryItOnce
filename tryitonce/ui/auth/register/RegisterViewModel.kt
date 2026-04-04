package com.TRY.tryitonce.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.TRY.tryitonce.domain.usecase.RegisterUseCase
import com.TRY.tryitonce.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegisterUiState(
    val displayName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val displayNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val isLoading: Boolean = false,
    val registerSuccess: Boolean = false,
    val errorMessage: String? = null,
) {
    val isSubmitEnabled: Boolean
        get() = !isLoading && displayName.isNotBlank() && email.isNotBlank()
                && password.isNotBlank() && confirmPassword.isNotBlank()
}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onDisplayNameChange(v: String) = _uiState.update { it.copy(displayName = v, displayNameError = null) }
    fun onEmailChange(v: String) = _uiState.update { it.copy(email = v, emailError = null) }
    fun onPasswordChange(v: String) = _uiState.update { it.copy(password = v, passwordError = null) }
    fun onConfirmPasswordChange(v: String) = _uiState.update { it.copy(confirmPassword = v, confirmPasswordError = null) }
    fun onErrorMessageShown() = _uiState.update { it.copy(errorMessage = null) }

    fun register() {
        val s = _uiState.value
        if (!s.isSubmitEnabled) return
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            when (val result = registerUseCase(s.displayName.trim(), s.email.trim(), s.password, s.confirmPassword)) {
                is Resource.Success -> _uiState.update { it.copy(isLoading = false, registerSuccess = true) }
                is Resource.Error -> {
                    val msg = result.message
                    _uiState.update {
                        it.copy(
                            isLoading            = false,
                            displayNameError     = msg.takeIf { it.contains("name", true) },
                            emailError           = msg.takeIf { it.contains("email", true) },
                            passwordError        = msg.takeIf { it.contains("password", true) && !it.contains("match", true) },
                            confirmPasswordError = msg.takeIf { it.contains("match", true) },
                            errorMessage         = msg.takeIf {
                                !it.contains("name", true) && !it.contains("email", true) && !it.contains("password", true)
                            },
                        )
                    }
                }
                is Resource.Loading -> Unit
            }
        }
    }
}
