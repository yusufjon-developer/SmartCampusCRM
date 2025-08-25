package com.smartcampus.presentation.ui.screen.security.permission

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.security.Permission
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

sealed interface PermissionContract {
    sealed interface Event : UiEvent {
        data object LoadPermission : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
    }

    data class State(
        val permissions: Flow<PagingData<Permission>> = emptyFlow(),
        val isLoading: Boolean = false,
        val expandedPermissionIds: Set<Int> = emptySet()
    ) : UiState
}