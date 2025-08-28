package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.smartcampus.crm.navigation.route.SecurityRoute
import com.smartcampus.presentation.ui.screen.security.SecurityScreen
import com.smartcampus.presentation.ui.screen.security.permission.PermissionScreen
import com.smartcampus.presentation.ui.screen.security.role.RoleScreen
import com.smartcampus.presentation.ui.screen.security.roleItem.RoleItemScreen

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
            RoleScreen(
                navigateToRoleItemScreen = { roleId ->
                    navController.navigate(SecurityRoute.RoleItem(roleId))
                }
            )
        }

        composable<SecurityRoute.RoleItem> { entry ->
            val args = entry.toRoute<SecurityRoute.RoleItem>()
            RoleItemScreen(roleId = args.roleId)
        }

        composable<SecurityRoute.Permission> {
            PermissionScreen()
        }
    }
}