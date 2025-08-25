package com.smartcampus.crm.data.manager

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import com.smartcampus.crm.data.preferencesKeys.THEME_KEY
import com.smartcampus.crm.domain.models.Theme
import com.smartcampus.crm.domain.models.managers.SettingsManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ExperimentalSettingsApi
class SettingsManagerImpl(
    private val settings: FlowSettings
) : SettingsManager {
    override fun getTheme(): Flow<Theme> =
        settings.getStringFlow(THEME_KEY, Theme.SYSTEM.name)
            .map { name ->
                try {
                    Theme.valueOf(name)
                } catch (_: IllegalArgumentException) {
                    Theme.SYSTEM
                }
            }

    override suspend fun setTheme(theme: Theme) {
        settings.putString(THEME_KEY, theme.name)
    }
}