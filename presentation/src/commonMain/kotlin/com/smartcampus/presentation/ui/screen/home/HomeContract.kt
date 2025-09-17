package com.smartcampus.presentation.ui.screen.home

import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

interface HomeContract {
    sealed interface Event : UiEvent {
        data object NavigateToAuditorium: Event
    }
    sealed interface Effect : UiEffect {
        data object NavigateToAuditorium: Effect
    }
    data class State(
        val isLoading: Boolean = false,
        val error: NetworkError? = null
    ) : UiState

}