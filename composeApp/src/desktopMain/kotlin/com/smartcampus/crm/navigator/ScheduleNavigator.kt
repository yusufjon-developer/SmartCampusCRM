package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartcampus.crm.navigation.route.ScheduleRoute
import com.smartcampus.presentation.ui.screen.schedule.ScheduleScreen

@Composable
fun ScheduleNavigator(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = ScheduleRoute.Schedule
    ) {
        composable<ScheduleRoute.Schedule> {
            ScheduleScreen()
        }
    }
}