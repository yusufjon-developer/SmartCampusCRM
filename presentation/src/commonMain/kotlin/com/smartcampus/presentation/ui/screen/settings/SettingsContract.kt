package com.smartcampus.presentation.ui.screen.settings

import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface SettingsContract {
    sealed interface Event : UiEvent {
        data object NavigateToTheme : Event
        data object Logout : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToTheme : Effect
    }

    data class State(
        val tmp: Int? = null
    ) : UiState
}