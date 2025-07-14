package com.smartcampus.crm.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable data object SignIn : Route
    @Serializable data object SignUp : Route
    @Serializable data class Main(val test: String) : Route
}