package com.smartcampus.presentation.ui.screen.home.group

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.GroupCreateRequest
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.models.GroupUpdateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface GroupContract {
    sealed interface Event : UiEvent {
        data object GetGroups : Event
        data class ShowAddDialog(val show: Boolean) : Event
        data class AddGroup(val request: GroupCreateRequest) : Event
        data class UpdateGroup(val id: Int, val request: GroupUpdateRequest) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
        data class ShowAddDialog(val show: Boolean) : Effect
    }

    data class State(
        val groups: Flow<PagingData<GroupDto>> = emptyFlow(),
        val isLoading: Boolean = false,
        val searchQuery: String = ""
    ) : UiState

}