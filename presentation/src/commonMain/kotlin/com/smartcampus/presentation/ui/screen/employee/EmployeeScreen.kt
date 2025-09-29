package com.smartcampus.presentation.ui.screen.employee

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.presentation.core.components.cards.FeatureCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.administration
import smartcampuscrm.presentation.generated.resources.employees
import smartcampuscrm.presentation.generated.resources.ic_employees
import smartcampuscrm.presentation.generated.resources.ic_it_department
import smartcampuscrm.presentation.generated.resources.ic_teacher
import smartcampuscrm.presentation.generated.resources.it_department
import smartcampuscrm.presentation.generated.resources.teachers

@Composable
fun EmployeeScreen(
    navigateToTeacher: () -> Unit,
    viewModel: EmployeeViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                EmployeeContract.Effect.NavigateToTeacher -> navigateToTeacher()
            }
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Adaptive(minSize = 196.dp)
    ) {
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_teacher),
                title = stringResource(Res.string.teachers),
                onClick = {
                    viewModel.setEvent(EmployeeContract.Event.NavigatToTeacher)
                }
            )
        }
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_teacher),
                title = stringResource(Res.string.administration),
                onClick = {
                    viewModel.setEvent(EmployeeContract.Event.NavigatToTeacher)
                }
            )
        }
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_it_department),
                title = stringResource(Res.string.it_department),
                onClick = {
                    viewModel.setEvent(EmployeeContract.Event.NavigatToTeacher)
                }
            )
        }
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_employees),
                title = stringResource(Res.string.employees),
                onClick = {
                    viewModel.setEvent(EmployeeContract.Event.NavigatToTeacher)
                }
            )
        }
    }
}