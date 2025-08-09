package com.smartcampus.presentation.ui.screen.security.role

import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface RoleContract {
    sealed interface Event : UiEvent {

    }

    sealed interface Effect : UiEffect {

    }

    data object State : UiState
}