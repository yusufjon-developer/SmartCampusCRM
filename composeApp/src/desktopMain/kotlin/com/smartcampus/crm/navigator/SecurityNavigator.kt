package com.smartcampus.crm.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartcampus.crm.navigation.route.SecurityRoute
import com.smartcampus.presentation.ui.screen.security.SecurityScreen

@Composable
fun SecurityNavigator(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = SecurityRoute.Security
    ) {
        composable<SecurityRoute.Security> {
            SecurityScreen(
                navigateToRole = { navController.navigate(SecurityRoute.Role) },
                navigateToPermission = { navController.navigate(SecurityRoute.Permission) }
            )
        }

        composable<SecurityRoute.Role> {
            Text("Роль", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        }

        composable<SecurityRoute.Permission> {
            Text("Доступы", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
        }
    }
}