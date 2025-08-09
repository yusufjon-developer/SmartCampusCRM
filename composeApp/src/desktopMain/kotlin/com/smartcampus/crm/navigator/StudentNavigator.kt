package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.smartcampus.crm.domain.models.Groups
import com.smartcampus.crm.domain.models.Specialities
import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.crm.navigation.route.ProfileRoute
import com.smartcampus.presentation.ui.screen.employee.studentProfile.StudentProfileScreen

@Composable
fun StudentNavigator(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ProfileRoute.Profile
    ) {
        composable<ProfileRoute.Profile> {
            StudentProfileScreen(
                id = 0,
                navigateToEdit = {_ ->},
                studentInfo = StudentInfo(Student(
                    groups = Groups(
                        1,
                        "group",
                        Specialities(
                            1,
                            "spec"
                        ),
                        2
                    )
                ))
            )
        }
    }
}