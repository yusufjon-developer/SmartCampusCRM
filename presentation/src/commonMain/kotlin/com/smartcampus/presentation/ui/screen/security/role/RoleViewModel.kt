package com.smartcampus.presentation.ui.screen.security.role

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.repositories.RoleRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class RoleViewModel(
    private val repository: RoleRepository
) : BaseViewModel<RoleContract.Event, RoleContract.Effect, RoleContract.State>() {

    override fun createInitialState(): RoleContract.State = RoleContract.State()

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            loadRoles()
        }

    }

    override fun handleEvent(event: RoleContract.Event) {
        viewModelScope.launch {
            when (event) {
                is RoleContract.Event.LoadRoles -> loadRoles()

                is RoleContract.Event.AddRole -> createRole(event.request)

                is RoleContract.Event.ShowAddDialog -> setEffect { RoleContract.Effect.ShowAddDialog(event.show) }

                is RoleContract.Event.NavigateToRoleItemScreen -> setEffect {
                    RoleContract.Effect.NavigateToRoleItemScreen(event.roleId)
                }
            }
        }
    }

    private fun loadRoles() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val rolesFlow = repository.getRoleList(sortBy = null)
                .cachedIn(viewModelScope)
            setState { copy(roles = rolesFlow, isLoading = false) }
        }
    }

    private suspend fun createRole(request: RoleRequest) {
        when (val result = repository.createRole(request).first()) {
            is Either.Right -> {
                setEffect { RoleContract.Effect.NavigateToRoleItemScreen(result.value.id) }
            }

            is Either.Left -> {
                setEffect { RoleContract.Effect.ShowError(result.value) }
            }
        }
    }
}
