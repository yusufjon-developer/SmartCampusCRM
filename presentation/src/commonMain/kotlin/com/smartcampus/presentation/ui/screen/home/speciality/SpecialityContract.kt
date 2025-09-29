package com.smartcampus.presentation.ui.screen.home.speciality

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.SpecialityCreateRequest
import com.smartcampus.crm.domain.models.SpecialityDto
import com.smartcampus.crm.domain.models.SpecialityUpdateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface SpecialityContract {
    sealed interface Event : UiEvent {
        data object GetSpecialities : Event
        data class ShowAddDialog(val show: Boolean) : Event
        data class AddSpeciality(val request: SpecialityCreateRequest) : Event
        data class UpdateSpeciality(val id: Int, val request: SpecialityUpdateRequest) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
        data class ShowAddDialog(val show: Boolean) : Effect
    }

    data class State(
        val specialities: Flow<PagingData<SpecialityDto>> = emptyFlow(),
        val isLoading: Boolean = false,
        val searchQuery: String = ""
    ) : UiState
}