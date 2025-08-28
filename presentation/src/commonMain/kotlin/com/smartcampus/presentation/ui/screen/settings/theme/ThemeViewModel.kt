package com.smartcampus.presentation.ui.screen.settings.theme

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.useCases.GetThemeUseCase
import com.smartcampus.crm.domain.useCases.SetThemeUseCase
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ThemeViewModel(
    private val getThemeUseCase: GetThemeUseCase,
    private val setThemeUseCase: SetThemeUseCase
) : BaseViewModel<ThemeContract.Event, ThemeContract.Effect, ThemeContract.State>() {
    override fun createInitialState() = ThemeContract.State()

    override fun handleEvent(event: ThemeContract.Event) {
        viewModelScope.launch {
            when (event) {
                ThemeContract.Event.GetTheme -> {
                    getThemeUseCase().collectLatest { theme ->
                        setState { copy(theme = theme) }
                    }
                }
                is ThemeContract.Event.SetTheme -> {
                    setThemeUseCase(event.theme)
                }
            }
        }
    }
}