package com.smartcampus.presentation.ui.screen.security.role

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.security.Role
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

sealed interface RoleContract {
    sealed interface Event : UiEvent {
        data object LoadRoles : Event
        data class DeleteRole(val roleId: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
    }

    data class State(
        val roles: Flow<PagingData<Role>> = emptyFlow(),
        val isLoading: Boolean = false,
        val expandedRoleIds: Set<Int> = emptySet()
    ) : UiState
}