package com.smartcampus.presentation.ui.screen.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
fun StudentScreen(
    navigateToStudentProfile: (Int) -> Unit,
    navigateToAddStudent: () -> Unit,
    viewModel: StudentViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val students = uiState.students.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StudentContract.Effect.NavigateToStudentProfile ->
                    navigateToStudentProfile(effect.id)
                is StudentContract.Effect.NavigateToAddStudent ->
                    navigateToAddStudent()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            if (uiState.isLoading) {
                LoadingIndicatorDialog(isLoading = true)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = students.itemCount,
                        key = students.itemKey { it.id }
                    ) { index ->
                        val student = students[index]
                        if (student != null) {
                            UserCard(
                                imageUrl = student.photo,
                                title = student.name,
                                role = "Student",
                                isActive = true
                            ) {
                                navigateToStudentProfile(student.id)
                            }
                        }
                    }

                    pagingLoadStateIndicator(
                        lazyPagingItems = students,
                        addButtonEnabled = true,
                        emptyListMessage = "Студенты не найдены",
                        onErrorAddClicked = { students.retry() }
                    ) {
                        AddButton(onClick = { navigateToAddStudent })
                    }
                }
            }
        }
    }
}
