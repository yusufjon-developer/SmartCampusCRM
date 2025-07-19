package com.smartcampus.presentation.ui.screen.login

import com.smartcampus.crm.domain.models.UserRequest
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

class LoginContract {
    sealed class Event : UiEvent {
        data class Login(val user: UserRequest) : Event()
    }
    sealed class Effect : UiEffect {
        data class Error(val message: String) : Effect()
        data object Success : Effect()
    }
    data class State(val isLoading: Boolean) : UiState
}