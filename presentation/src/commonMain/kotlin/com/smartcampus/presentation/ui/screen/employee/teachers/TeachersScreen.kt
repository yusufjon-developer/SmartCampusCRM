package com.smartcampus.presentation.ui.screen.employee.teachers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.smartcampus.presentation.core.components.button.AddButton
import com.smartcampus.presentation.core.components.cards.UserCard
import com.smartcampus.presentation.core.components.form.LoadingIndicatorDialog
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeachersScreen(
    navigateToTeacherProfile: (Int) -> Unit,
    navigateToAddTeacher: () -> Unit,
    viewModel: TeachersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val teachers = uiState.teachers.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is TeachersContract.Effect.NavigateToTeacherProfile ->
                    navigateToTeacherProfile(effect.id)

                is TeachersContract.Effect.NavigateToAddTeacher ->
                    navigateToAddTeacher()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            if (uiState.isLoading) {
                LoadingIndicatorDialog(isLoading = true)
            } else {
                AddButton(
                    onClick = { viewModel.setEvent(TeachersContract.Event.AddTeacher) },
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    shape = RoundedCornerShape(12.dp)
                )

                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    columns = GridCells.Adaptive(minSize = 160.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(
                        count = teachers.itemCount,
                        key = teachers.itemKey { it.id }
                    ) { index ->
                        val teacher = teachers[index]
                        if (teacher != null) {
                            UserCard(
                                imageUrl = null,
                                title = teacher.name,
                                role = "Teacher",
                                isActive = true
                            ) {
                                navigateToTeacherProfile(teacher.id)
                            }
                        }
                    }

                    pagingLoadStateIndicator(
                        lazyPagingItems = teachers,
                        emptyListMessage = "Преподаватели не найдены",
                        onErrorAddClicked = { teachers.retry() }
                    )
                }
            }
        }
    }
}
