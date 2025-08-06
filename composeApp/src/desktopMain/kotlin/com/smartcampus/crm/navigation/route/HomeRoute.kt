package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    @Serializable data object Home: HomeRoute
}