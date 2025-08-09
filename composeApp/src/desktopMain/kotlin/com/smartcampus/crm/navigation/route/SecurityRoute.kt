package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface SecurityRoute {
    @Serializable data object Security
    @Serializable data object Role
    @Serializable data object Permission
}