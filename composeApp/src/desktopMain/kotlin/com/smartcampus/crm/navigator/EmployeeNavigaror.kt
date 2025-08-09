package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartcampus.crm.navigation.route.EmployeeRoute
import com.smartcampus.presentation.ui.screen.employee.EmployeeScreen
import com.smartcampus.presentation.ui.screen.employee.administration.AdministrationScreen
import com.smartcampus.presentation.ui.screen.employee.itDepartment.ITDeportmentScreen
import com.smartcampus.presentation.ui.screen.employee.teachers.TeachersScreen

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
            TeachersScreen()
        }

        composable<EmployeeRoute.Administration> {
            AdministrationScreen()
        }

        composable<EmployeeRoute.ITDepartment> {
            ITDeportmentScreen()
        }

    }
}