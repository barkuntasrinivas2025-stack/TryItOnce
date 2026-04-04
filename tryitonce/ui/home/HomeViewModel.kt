package com.TRY.tryitonce.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.TRY.tryitonce.domain.model.User
import com.TRY.tryitonce.domain.usecase.GetCurrentUserUseCase
import com.TRY.tryitonce.domain.usecase.IsLoggedInUseCase
import com.TRY.tryitonce.domain.usecase.LogoutUseCase
import com.TRY.tryitonce.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val logoutSuccess: Boolean = false,
    val errorMessage: String? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    val currentUser: StateFlow<User?> = getCurrentUserUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoggedIn.value = isLoggedInUseCase()
        }
    }

    fun onErrorMessageShown() = _uiState.update { it.copy(errorMessage = null) }

    fun logout() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            when (val r = logoutUseCase()) {
                is Resource.Success -> _uiState.update { it.copy(isLoading = false, logoutSuccess = true) }
                is Resource.Error   -> _uiState.update { it.copy(isLoading = false, errorMessage = r.message) }
                is Resource.Loading -> Unit
            }
        }
    }
}
