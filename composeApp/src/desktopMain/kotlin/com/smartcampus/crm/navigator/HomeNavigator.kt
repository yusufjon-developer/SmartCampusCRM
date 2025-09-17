package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartcampus.crm.navigation.route.HomeRoute
import com.smartcampus.presentation.ui.screen.home.HomeScreen
import com.smartcampus.presentation.ui.screen.home.auditorium.AuditoriumScreen

@Composable
fun HomeNavigator(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute.Home
    ) {
        composable<HomeRoute.Home> {
            HomeScreen(
                onNavigatorToAuditorium = {
                    navController.navigate(HomeRoute.Auditorium)
                }
            )
        }
        composable<HomeRoute.Auditorium> {
            AuditoriumScreen()
        }
    }
}