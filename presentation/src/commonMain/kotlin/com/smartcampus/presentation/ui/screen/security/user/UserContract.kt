package com.smartcampus.presentation.ui.screen.security.user

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.security.User
import com.smartcampus.crm.domain.models.security.UserRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

sealed interface UserContract {
    sealed interface Event : UiEvent {
        data object LoadUsers : Event
        data class AddUser(val request: UserRequest) : Event
        data class ShowAddDialog(val show: Boolean) : Event
        data class NavigateToUserPermissionScreen(val userId: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
        data class ShowAddDialog(val show: Boolean) : Effect
        data class NavigateToUserPermissionScreen(val userId: Int) : Effect
    }

    data class State(
        val users: Flow<PagingData<User>> = emptyFlow(),
        val isLoading: Boolean = false,
        val expandedUserIds: Set<Int> = emptySet()
    ) : UiState
}