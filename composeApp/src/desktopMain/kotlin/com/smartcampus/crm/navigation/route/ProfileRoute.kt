package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface ProfileRoute {
    @Serializable data object Profile
}