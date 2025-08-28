package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartcampus.crm.navigation.route.SettingsRoute
import com.smartcampus.presentation.ui.screen.settings.SettingsScreen
import com.smartcampus.presentation.ui.screen.settings.theme.ThemeScreen

@Composable
fun SettingsNavigator(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SettingsRoute.Settings
    ) {
        composable<SettingsRoute.Settings> {
            SettingsScreen(
                navigateToTheme = {
                    navController.navigate(SettingsRoute.Theme)
                }
            )
        }

        composable<SettingsRoute.Theme> {
            ThemeScreen()
        }
    }
}