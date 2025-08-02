package com.smartcampus.presentation.ui.screen.settings

import com.smartcampus.presentation.core.base.BaseViewModel

class SettingsViewModel(

) : BaseViewModel<SettingsContract.Event, SettingsContract.Effect, SettingsContract.State>() {
    override fun createInitialState() = SettingsContract.State()

    override fun handleEvent(event: SettingsContract.Event) {
        when(event) {
            SettingsContract.Event.GoToTheme -> {
                setEffect { SettingsContract.Effect.NavigateToTheme }
            }
        }
    }
}