package com.smartcampus.crm.data.manager

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import com.smartcampus.crm.data.preferencesKeys.KEY_ACCESS_TOKEN
import com.smartcampus.crm.domain.managers.TokenManager
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class TokenManagerImpl(
    private val settings: FlowSettings
) : TokenManager {
    override suspend fun saveAccessToken(token: String) {
        settings.putString(KEY_ACCESS_TOKEN, token)
    }

    override suspend fun getAccessToken(): Flow<String?> {
        return settings.getStringOrNullFlow(KEY_ACCESS_TOKEN)
    }

    override suspend fun deleteAccessToken() {
        settings.remove(KEY_ACCESS_TOKEN)
    }
}