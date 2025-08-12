package com.smartcampus.presentation.ui.screen.security.permission

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.models.security.PermissionRequest
import com.smartcampus.crm.domain.useCases.PermissionUseCases
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import com.smartcampus.presentation.ui.screen.security.permission.PermissionContract.Effect.ShowAddDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class PermissionViewModel(
    private val permissionUseCases: PermissionUseCases
) : BaseViewModel<PermissionContract.Event, PermissionContract.Effect, PermissionContract.State>() {
    override fun createInitialState() = PermissionContract.State()

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            loadPermissions()
        }

    }

    override fun handleEvent(event: PermissionContract.Event) {
        viewModelScope.launch {
            when (event) {
                is PermissionContract.Event.LoadPermission -> loadPermissions()

                is PermissionContract.Event.DeletePermission -> deletePermission(event.permissionId)

                is PermissionContract.Event.AddPermission -> createPermission(event.request)

                is PermissionContract.Event.ShowAddDialog -> setEffect { ShowAddDialog(event.show) }

            }
        }
    }

    private fun loadPermissions() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val permissionsFlow = permissionUseCases.getPermissionList(sortedBy = null)
                .cachedIn(viewModelScope)
            setState { copy(permissions = permissionsFlow, isLoading = false) }
        }
    }

    private suspend fun deletePermission(permissoinId: Int) {
        when (val result = permissionUseCases.deletePermissionById(id = permissoinId).firstOrNull()) {
            is Either.Right -> {
                if (result.value) {
                    setEffect { PermissionContract.Effect.ShowSuccessMessage("Доступ успешно удалена") }
                }
            }

            is Either.Left -> {
                setEffect { PermissionContract.Effect.ShowError(result.value) }
            }

            null -> {
                setEffect { PermissionContract.Effect.ShowError(NetworkError.Unexpected("Ошибка при удалении доступа: нет ответа")) }
            }
        }
    }

    private suspend fun createPermission(request: PermissionRequest) {
        when (val result = permissionUseCases.createPermission(request).first()) {
            is Either.Right -> {
                setEffect { PermissionContract.Effect.ShowSuccessMessage("Доступ успешно создана") }
            }

            is Either.Left -> {
                setEffect { PermissionContract.Effect.ShowError(result.value) }
            }
        }
    }
}