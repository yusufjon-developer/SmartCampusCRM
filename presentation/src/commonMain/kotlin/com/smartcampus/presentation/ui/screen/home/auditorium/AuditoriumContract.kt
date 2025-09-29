package com.smartcampus.presentation.ui.screen.home.auditorium

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.AuditoriumCreateRequest
import com.smartcampus.crm.domain.models.AuditoriumDto
import com.smartcampus.crm.domain.models.AuditoriumUpdateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface AuditoriumContract {
    sealed interface Event : UiEvent {
        data object GetAuditoriums : Event
        data class ShowAddDialog(val show: Boolean) : Event
        data class AddAuditorium(val request: AuditoriumCreateRequest) : Event
        data class UpdateAuditorium(val id: Int, val request: AuditoriumUpdateRequest) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
        data class ShowAddDialog(val show: Boolean) : Effect
    }

    data class State(
        val auditoriums: Flow<PagingData<AuditoriumDto>> = emptyFlow(),
        val isLoading: Boolean = false,
        val searchQuery: String = ""
    ) : UiState

}