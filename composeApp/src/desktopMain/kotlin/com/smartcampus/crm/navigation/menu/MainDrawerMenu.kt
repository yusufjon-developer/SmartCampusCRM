package com.smartcampus.crm.navigation.menu

import kotlinx.serialization.Serializable

@Serializable
sealed interface MainDrawerMenu {
    @Serializable data object Home : MainDrawerMenu
    @Serializable data object Settings : MainDrawerMenu
    @Serializable data object Profile : MainDrawerMenu
    @Serializable data object Schedule : MainDrawerMenu
    @Serializable data object Employees : MainDrawerMenu
    @Serializable data object Security : MainDrawerMenu
}