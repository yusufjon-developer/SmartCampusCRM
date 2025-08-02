package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface SettingsRoute {
    @Serializable data object Settings
    @Serializable data object Theme
}