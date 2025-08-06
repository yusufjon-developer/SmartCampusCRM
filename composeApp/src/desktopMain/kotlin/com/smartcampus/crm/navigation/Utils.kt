package com.smartcampus.crm.navigation

import androidx.navigation.NavHostController

fun NavHostController.safelyPopBackStack(): Boolean {
    val canPop = previousBackStackEntry != null
    val currentRoute = currentBackStackEntry?.destination?.route
    val startRoute = graph.startDestinationRoute

    return if (canPop && currentRoute != startRoute) {
        popBackStack()
    } else {
        false
    }
}