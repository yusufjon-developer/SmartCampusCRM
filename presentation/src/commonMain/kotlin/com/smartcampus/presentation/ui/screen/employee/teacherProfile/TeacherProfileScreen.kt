package com.smartcampus.presentation.ui.screen.employee.teacherProfile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeacherProfileScreen(
    id: Int,
    viewModel: TeacherProfileViewModel = koinViewModel(),
) {
    LaunchedEffect(id) {
        viewModel.setEvent(TeacherProfileContract.Event.GetTeacherInfo(id))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is TeacherProfileContract.Effect.NavigateToEdit -> { /*TODO*/ }
            }
        }
    }



}