package com.smartcampus.presentation.ui.screen.settings

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.repositories.SessionManager
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val sessionManager: SessionManager
) : BaseViewModel<SettingsContract.Event, SettingsContract.Effect, SettingsContract.State>() {
    override fun createInitialState() = SettingsContract.State()

    override fun handleEvent(event: SettingsContract.Event) {
        viewModelScope.launch {
            when (event) {
                SettingsContract.Event.NavigateToTheme -> {
                    setEffect { SettingsContract.Effect.NavigateToTheme }
                }

                SettingsContract.Event.Logout -> sessionManager.notifyForceLogout()
            }
        }
    }
}