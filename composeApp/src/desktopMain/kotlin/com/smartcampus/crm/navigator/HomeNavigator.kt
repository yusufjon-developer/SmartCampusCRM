package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.smartcampus.crm.navigation.route.HomeRoute
import com.smartcampus.presentation.ui.screen.home.HomeScreen
import com.smartcampus.presentation.ui.screen.home.auditorium.AuditoriumScreen
import com.smartcampus.presentation.ui.screen.home.group.GroupScreen
import com.smartcampus.presentation.ui.screen.home.group.studentGroup.StudentGroupScreen
import com.smartcampus.presentation.ui.screen.home.speciality.SpecialityScreen
import com.smartcampus.presentation.ui.screen.student.studentProfile.StudentProfileScreen

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
                },
                onNavigatorToSpeciality = {
                    navController.navigate(HomeRoute.Speciality)
                },
                onNavigatorToGroup = {
                    navController.navigate(HomeRoute.Group)
                }
            )
        }

        composable<HomeRoute.Auditorium> {
            AuditoriumScreen()
        }

        composable<HomeRoute.Group> {
            GroupScreen(
                onNavigatorToStudentGroup = { groupId ->
                    navController.navigate(HomeRoute.StudentGroup(groupId))
                }
            )
        }

        composable<HomeRoute.Speciality> {
            SpecialityScreen()
        }

        composable<HomeRoute.StudentGroup> {backStackEntry ->
            val args = backStackEntry.toRoute<HomeRoute.StudentGroup>()
            StudentGroupScreen(
                groupId = args.groupId,
                navigateToStudentProfile = { studentId ->
                    navController.navigate(HomeRoute.StudentProfile(studentId))
                }
            )
        }

        composable<HomeRoute.StudentProfile> {
            val args = it.toRoute<HomeRoute.StudentProfile>()
            StudentProfileScreen(
                id = args.studentId,
                onAddStudent = {  }
            )
        }
    }
}