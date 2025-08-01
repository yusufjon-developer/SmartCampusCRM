package com.smartcampus.crm.navigator

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.smartcampus.crm.domain.models.Groups
import com.smartcampus.crm.domain.models.Specialities
import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.presentation.ui.screen.studentProfile.StudentProfileScreen

@Composable
fun StudentNavigator(navController: NavController) {
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