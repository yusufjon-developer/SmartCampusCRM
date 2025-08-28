package com.smartcampus.presentation.ui.screen.security

import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface SecurityContract {
    sealed interface Event : UiEvent {
        data object NavigateToRole : Event
        data object NavigateToPermission : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToRole : Effect
        data object NavigateToPermission : Effect
    }

    data object State: UiState
}