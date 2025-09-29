package com.smartcampus.presentation.ui.screen.login

import com.smartcampus.crm.domain.models.LoginRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

class LoginContract {
    sealed class Event : UiEvent {
        data object Login : Event()
        data class EmailChanged(val email: String) : Event()
        data class PasswordChanged(val password: String) : Event()
    }

    sealed class Effect : UiEffect {
        data object Error : Effect()
        data object Success : Effect()
    }

    data class State(
        val isLoading: Boolean = false,
        val request: LoginRequest = LoginRequest("", "", ""),
        val error: NetworkError? = null
    ) : UiState
}