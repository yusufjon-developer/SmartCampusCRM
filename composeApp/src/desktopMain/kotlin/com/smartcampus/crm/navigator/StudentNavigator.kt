package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.smartcampus.crm.navigation.route.ProfileRoute
import com.smartcampus.presentation.ui.screen.student.StudentScreen
import com.smartcampus.presentation.ui.screen.student.studentProfile.StudentProfileScreen

@Composable
fun StudentNavigator(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ProfileRoute.Profile
    ) {
        composable<ProfileRoute.Profile> {
            StudentScreen(
                navigateToStudentProfile = { id ->
                    navController.navigate(ProfileRoute.StudentProfile(id))
                },
                navigateToAddStudent = {
                    navController.navigate(ProfileRoute.StudentProfile(null))
                }
            )
        }

        composable<ProfileRoute.StudentProfile> { backStackEntry ->
            val args = backStackEntry.toRoute<ProfileRoute.StudentProfile>()
            StudentProfileScreen(
                id = args.studentId
            )
        }

    }
}