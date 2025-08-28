package com.smartcampus.presentation.ui.screen.settings.theme

import com.smartcampus.crm.domain.models.Theme
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface ThemeContract {
    sealed interface Event : UiEvent {
        data object GetTheme : Event
        data class SetTheme(val theme: Theme) : Event
    }

    sealed interface Effect : UiEffect

    data class State(
        val theme: Theme? = null
    ) : UiState
}