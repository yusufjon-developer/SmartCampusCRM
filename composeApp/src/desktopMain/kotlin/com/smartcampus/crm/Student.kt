package com.smartcampus.crm

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.smartcampus.crm.domain.models.Groups
import com.smartcampus.crm.domain.models.Specialities
import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.presentation.ui.screen.studentProfile.StudentProfileScreen

@Composable
fun Student(navController: NavController) {
    StudentProfileScreen(
        id = 0,
        navigateToEdit = {_ ->},
        studentInfo = com.smartcampus.crm.domain.models.student.StudentInfo(Student(
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