package com.smartcampus.presentation.ui.widgets.auditoriumDropdown

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.AuditoriumDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface AuditoriumDropdownContract {

    sealed interface Event : UiEvent {
        data class LoadAuditoriums(val isAvailable: Boolean, val day: String) : Event
        data class ToggleDropdown(val expanded: Boolean) : Event
        data class SelectAuditorium(val auditorium: AuditoriumDto?) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
    }

    data class State(
        val auditoriums: Flow<PagingData<AuditoriumDto>> = emptyFlow(),
        val expanded: Boolean = false,
        val isLoading: Boolean = false,
        val selectedAuditorium: AuditoriumDto? = null,
        val error: NetworkError? = null
    ) : UiState
}