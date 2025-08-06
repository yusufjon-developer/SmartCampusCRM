package com.smartcampus.presentation.ui.screen.employee

import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface EmployeeContract {
    sealed interface Event : UiEvent {
        data object NavigatToTeacher : Event
    }

    sealed interface Effect : UiEffect {
        data object NavigateToTeacher : Effect
    }

    data class State(
        val tmp: Int? = null
    ) : UiState
}