package com.smartcampus.presentation.ui.screen.security.userPermission

import com.smartcampus.crm.domain.models.security.UpdateUserPermissions
import com.smartcampus.crm.domain.models.security.UserPermission
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

interface UserPermissionContract {
    sealed interface Event : UiEvent {
        data class LoadUser(val userId: Int) : Event
        data class UpdateUserPermission(val request: UpdateUserPermissions) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
    }

    data class State(
        val user: UserPermission = UserPermission(
            userId = 0,
            username = "",
            isActive = false,
            userDevices = emptyList(),
            permissions = emptyList()
        ),
        val isLoading: Boolean = false,
    ) : UiState
}