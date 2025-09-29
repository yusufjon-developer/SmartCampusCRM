package com.smartcampus.presentation.ui.screen.security.permission

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.repositories.SecurityRepository
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.launch

class PermissionViewModel(
    private val repository: SecurityRepository
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
            }
        }
    }

    private fun loadPermissions() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val permissionsFlow = this@PermissionViewModel.repository.getPermissionList(sortBy = null)
                .cachedIn(viewModelScope)
            setState { copy(permissions = permissionsFlow, isLoading = false) }
        }
    }
}