package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.models.Theme
import com.smartcampus.crm.domain.managers.SettingsManager

class SetThemeUseCase(
    private val manager: SettingsManager
) {
    suspend operator fun invoke(theme: Theme) = manager.setTheme(theme)
}