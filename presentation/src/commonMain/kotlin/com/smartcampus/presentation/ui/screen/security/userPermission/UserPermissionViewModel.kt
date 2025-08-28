package com.smartcampus.presentation.ui.screen.security.userPermission

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.repositories.UserRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionContract.Event.DeleteUser
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionContract.Event.LoadUser
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionContract.Event.UpdateUserPermission
import kotlinx.coroutines.delay
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
                delay(2000)
                setEffect { UserPermissionContract.Effect.ShowSuccessMessage("Success") }
            }

            is DeleteUser -> scope.launch {
                deleteUser(event.userId)
            }

            is UpdateUserPermission -> scope.launch {
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

    private suspend fun deleteUser(userId: Int) {

    }
}