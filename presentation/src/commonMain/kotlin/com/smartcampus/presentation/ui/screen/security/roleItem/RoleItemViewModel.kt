package com.smartcampus.presentation.ui.screen.security.roleItem

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import com.smartcampus.crm.domain.repositories.RoleRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class RoleItemViewModel(
    private val repository: RoleRepository,
) : BaseViewModel<RoleItemContract.Event, RoleItemContract.Effect, RoleItemContract.State>() {

    override fun createInitialState(): RoleItemContract.State = RoleItemContract.State()
    private val scope get() = viewModelScope

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
        }

    }

    override fun handleEvent(event: RoleItemContract.Event) {

        when (event) {
            is RoleItemContract.Event.LoadRole -> scope.launch {
                getRole(event.roleId)
            }

            is RoleItemContract.Event.DeleteRole -> scope.launch {
                deleteRole(event.roleId)
            }

            is RoleItemContract.Event.UpdateRolePermission -> scope.launch {
                updatePermission(event.request)
            }
        }
    }

    private suspend fun getRole(roleId: Int) {
        when (val result = repository.getRoleById(id = roleId).first()) {
            is Either.Right -> {
                setState { copy(role = result.value, isLoading = false) }
            }

            is Either.Left -> {
                setEffect { RoleItemContract.Effect.ShowError(result.value) }
            }
        }
    }

    private suspend fun deleteRole(roleId: Int) {
        when (val result = repository.deleteRole(id = roleId).first()) {
            is Either.Right -> {
                if (result.value) {
                    setEffect { RoleItemContract.Effect.ShowSuccessMessage("Роль успешно удалена") }
                }
            }

            is Either.Left -> {
                setEffect { RoleItemContract.Effect.ShowError(result.value) }
            }
        }
    }

    private suspend fun updatePermission(request: UpdatePermissionStatus) {
        when (val result =
            repository.updateRolePermissions(uiState.value.role.roleId, request).first()) {
            is Either.Right -> {
                setEffect { RoleItemContract.Effect.ShowSuccessMessage("Роль успешно обновлена") }
            }

            is Either.Left -> {
                setEffect { RoleItemContract.Effect.ShowError(result.value) }
            }
        }
    }
}
