package com.smartcampus.presentation.ui.screen.security.user

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.repositories.UserRepository
import com.smartcampus.presentation.core.base.BaseViewModel
import com.smartcampus.presentation.ui.screen.security.user.UserContract.Event.AddUser
import com.smartcampus.presentation.ui.screen.security.user.UserContract.Event.LoadUsers
import com.smartcampus.presentation.ui.screen.security.user.UserContract.Event.NavigateToUserPermissionScreen
import com.smartcampus.presentation.ui.screen.security.user.UserContract.Event.ShowAddDialog
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository
) : BaseViewModel<UserContract.Event, UserContract.Effect, UserContract.State>() {
    override fun createInitialState(): UserContract.State = UserContract.State()

    init {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            loadUsers()
        }

    }

    override fun handleEvent(event: UserContract.Event) {
        when (event) {
            LoadUsers -> loadUsers()

            is AddUser -> {}

            is ShowAddDialog -> setEffect { UserContract.Effect.ShowAddDialog(event.show) }

            is NavigateToUserPermissionScreen -> setEffect {
                UserContract.Effect.NavigateToUserPermissionScreen(event.userId)
            }
        }
    }

    private fun loadUsers() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val UsersFlow = repository.getUserList(sortBy = null)
                .cachedIn(viewModelScope)
            setState { copy(users = UsersFlow, isLoading = false) }
        }
    }
}