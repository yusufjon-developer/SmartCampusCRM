package com.smartcampus.presentation.ui.screen.security.rolePermission

import com.smartcampus.crm.domain.models.security.RolePermission
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface RolePermissionContract {
    sealed interface Event : UiEvent {
        data class LoadRole(val roleId: Int) : Event
        data class UpdateRolePermission(val request: UpdatePermissionStatus) : Event
        data class DeleteRole(val roleId: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
    }

    data class State(
        val role: RolePermission = RolePermission(
            roleId = 0,
            name = "",
            description = "",
            permissions = emptyList()
        ),
        val isLoading: Boolean = false,
    ) : UiState
}