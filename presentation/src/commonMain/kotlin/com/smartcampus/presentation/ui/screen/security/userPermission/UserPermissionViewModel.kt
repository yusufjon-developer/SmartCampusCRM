package com.smartcampus.presentation.ui.screen.security.userPermission

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.models.security.UpdateUserPermissions
import com.smartcampus.crm.domain.repositories.UserRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionContract.Event.LoadUser
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionContract.Event.UpdateUserPermission
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UserPermissionViewModel(
    private val repository: UserRepository
) : BaseViewModel<UserPermissionContract.Event, UserPermissionContract.Effect, UserPermissionContract.State>()  {

    override fun createInitialState(): UserPermissionContract.State = UserPermissionContract.State()

    private val scope get() = viewModelScope

    override fun handleEvent(event: UserPermissionContract.Event) {
        when (event) {
            is LoadUser -> scope.launch {
                getUser(event.userId)
                setEffect { UserPermissionContract.Effect.ShowSuccessMessage("Success") }
            }

            is UpdateUserPermission -> scope.launch {
                updateUserPermission(event.request)
            }
        }
    }

    private suspend fun getUser(userId: Int) {
        when(val result = repository.getUserById(id = userId).first()) {
            is Either.Right -> {
                setState { copy(user = result.value, isLoading = false) }
            }

            is Either.Left -> {
                setEffect { UserPermissionContract.Effect.ShowError(result.value) }
            }
        }
    }

    private suspend fun updateUserPermission(request: UpdateUserPermissions) {
        when (val result =
            repository.updateUserPermission(uiState.value.user.userId, request).first()) {
            is Either.Right -> {
                setEffect { UserPermissionContract.Effect.ShowSuccessMessage("Update successfully") }
                setState { copy(user = result.value, isLoading = false) }
            }
            is Either.Left -> {
                setEffect { UserPermissionContract.Effect.ShowError(result.value) }
            }
        }
    }

}