package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.managers.SettingsManager

class GetThemeUseCase(
    private val manager: SettingsManager
) {
    operator fun invoke() = manager.getTheme()
}