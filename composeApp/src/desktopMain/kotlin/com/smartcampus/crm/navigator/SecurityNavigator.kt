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
import com.smartcampus.presentation.ui.screen.security.rolePermission.RoleItemScreen
import com.smartcampus.presentation.ui.screen.security.user.UserScreen
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionScreen

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
                navigateToPermission = { navController.navigate(SecurityRoute.Permission) },
                navigateToUser = { navController.navigate(SecurityRoute.User) }
            )
        }

        composable<SecurityRoute.Role> {
            RoleScreen(
                navigateToRoleItemScreen = { roleId ->
                    navController.navigate(SecurityRoute.RolePermission(roleId))
                }
            )
        }

        composable<SecurityRoute.RolePermission> { entry ->
            val args = entry.toRoute<SecurityRoute.RolePermission>()
            RoleItemScreen(roleId = args.roleId)
        }

        composable<SecurityRoute.Permission> {
            PermissionScreen()
        }

        composable<SecurityRoute.User> {
            UserScreen(
                navigateToUserPermissionScreen = { userId ->
                    navController.navigate(SecurityRoute.UserPermission(userId))
                }
            )
        }

        composable<SecurityRoute.UserPermission> {
            val args = it.toRoute<SecurityRoute.UserPermission>()
            UserPermissionScreen(userId = args.userId)
        }


    }
}