package com.smartcampus.presentation.ui.screen.employee.teachers

import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface TeachersContract {
    sealed interface Event : UiEvent {
        object NavigatToTeacher : Event
    }

    sealed interface Effect : UiEffect {
        object NavigateToTeacher : Effect
    }

    data class State(
        val isLoading: Boolean = false
    ) : UiState
}