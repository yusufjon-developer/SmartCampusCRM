package com.smartcampus.crm.domain.managers

import com.smartcampus.crm.domain.models.Theme
import kotlinx.coroutines.flow.Flow

interface SettingsManager {
    fun getTheme(): Flow<Theme>
    suspend fun setTheme(theme: Theme)
}