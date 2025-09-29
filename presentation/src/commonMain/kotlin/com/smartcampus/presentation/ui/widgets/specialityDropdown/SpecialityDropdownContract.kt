package com.smartcampus.presentation.ui.widgets.specialityDropdown

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.SpecialityDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface SpecialityDropdownContract {
    sealed interface Event : UiEvent {
        object LoadSpecialties : Event
        data class ToggleDropdown(val expanded: Boolean) : Event
        data class SelectSpeciality(val speciality: SpecialityDto) : Event
        data class SearchSpecialties(val query: String) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
    }

    data class State(
        val specialties: Flow<PagingData<SpecialityDto>> = emptyFlow(),
        val expanded: Boolean = false,
        val isLoading: Boolean = false,
        val selectedSpeciality: SpecialityDto? = null,
        val searchQuery: String = "",
        val error: NetworkError? = null
    ) : UiState
}