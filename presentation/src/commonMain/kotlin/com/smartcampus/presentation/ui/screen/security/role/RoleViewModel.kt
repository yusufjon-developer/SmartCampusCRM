package com.smartcampus.presentation.ui.screen.security.role

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.useCases.RoleUseCases
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class RoleViewModel(
    private val roleUseCases: RoleUseCases
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
                is RoleContract.Event.LoadRoles -> {
                    loadRoles()
                }

                is RoleContract.Event.DeleteRole -> {
                    deleteRole(event.roleId)
                }
            }
        }
    }

    private fun loadRoles() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val rolesFlow = roleUseCases.getRoleList(sortedBy = null)
                .cachedIn(viewModelScope)
            setState { copy(roles = rolesFlow, isLoading = false) }
        }
    }

    private suspend fun deleteRole(roleId: Int) {
        when (val result = roleUseCases.deleteRoleById(id = roleId).firstOrNull()) {
            is Either.Right -> {
                if (result.value) {
                    setEffect { RoleContract.Effect.ShowSuccessMessage("Роль успешно удалена") }
                }
            }

            is Either.Left -> {
                setEffect { RoleContract.Effect.ShowError(result.value) }
            }

            null -> {
                setEffect { RoleContract.Effect.ShowError(NetworkError.Unexpected("Ошибка при удалении роли: нет ответа")) }
            }
        }
    }
}
