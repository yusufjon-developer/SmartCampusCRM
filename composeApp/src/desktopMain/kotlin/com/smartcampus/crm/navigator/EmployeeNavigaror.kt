package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.smartcampus.crm.navigation.route.EmployeeRoute
import com.smartcampus.presentation.ui.screen.employee.EmployeeScreen
import com.smartcampus.presentation.ui.screen.employee.administration.AdministrationScreen
import com.smartcampus.presentation.ui.screen.employee.itDepartment.ITDeportmentScreen
import com.smartcampus.presentation.ui.screen.employee.teachers.TeachersScreen
import com.smartcampus.presentation.ui.screen.employee.teachers.teacherProfile.TeacherProfileScreen

@Composable
fun EmployeeNavigator(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = EmployeeRoute.Employee
    ) {
        composable<EmployeeRoute.Employee> {
            EmployeeScreen(
                navigateToTeacher = {
                    navController.navigate(EmployeeRoute.Teachers)
                }
            )
        }

        composable<EmployeeRoute.Teachers> {
            TeachersScreen(
                navigateToTeacherProfile = { id ->
                    navController.navigate(EmployeeRoute.TeacherProfile(id))
                },

                navigateToAddTeacher = {
                    navController.navigate(EmployeeRoute.TeacherProfile(null))
                }
            )
        }

        composable<EmployeeRoute.Administration> {
            AdministrationScreen()
        }

        composable<EmployeeRoute.ITDepartment> {
            ITDeportmentScreen()
        }

        composable<EmployeeRoute.TeacherProfile> {backStackEntry ->
            val args = backStackEntry.toRoute<EmployeeRoute.TeacherProfile>()
            TeacherProfileScreen(
                id = args.teacherId,
                onAddTeacher = {id ->
                    navController.navigate(EmployeeRoute.TeacherProfile(id)) {
                    popUpTo(navController.currentDestination?.route ?: return@navigate) {
                        inclusive = true
                    }
                    launchSingleTop = true
                }
                },
                onBackAfterDelete = {
                    navController.popBackStack()
                }
            )

        }

    }
}