package com.smartcampus.presentation.ui.widgets.groupDropdown

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface GroupDropdownContract {

    sealed interface Event : UiEvent {
        object LoadGroups : Event
        data class ToggleDropdown(val expanded: Boolean) : Event
        data class SelectGroup(val group: GroupDto) : Event
        data class SearchGroups(val query: String) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
    }

    data class State(
        val groups: Flow<PagingData<GroupDto>> = emptyFlow(),
        val expanded: Boolean = false,
        val isLoading: Boolean = false,
        val selectedGroup: GroupDto? = null,
        val searchQuery: String = "",
        val error: NetworkError? = null
    ) : UiState
}