package com.smartcampus.presentation.ui.screen.security

import androidx.lifecycle.viewModelScope
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.launch

class SecurityViewModel(

) : BaseViewModel<SecurityContract.Event, SecurityContract.Effect, SecurityContract.State>() {
    override fun createInitialState() = SecurityContract.State

    override fun handleEvent(event: SecurityContract.Event) {
        viewModelScope.launch {
            when (event) {
                SecurityContract.Event.NavigateToRole -> {
                    setEffect { SecurityContract.Effect.NavigateToRole }
                }

                SecurityContract.Event.NavigateToPermission -> {
                    setEffect { SecurityContract.Effect.NavigateToPermission }
                }

                SecurityContract.Event.NavigateToUser -> {
                    setEffect { SecurityContract.Effect.NavigateToUser }
                }
            }
        }
    }
}