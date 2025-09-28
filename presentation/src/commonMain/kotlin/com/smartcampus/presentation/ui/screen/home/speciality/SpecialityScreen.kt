package com.smartcampus.presentation.ui.screen.home.speciality

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.smartcampus.crm.domain.models.SpecialityCreateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.button.AddButton
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.text.InfoItem
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SpecialityScreen(
    viewModel: SpecialityViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val specialities = uiState.specialities.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    var newSpecialityName by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    var showAddDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf<NetworkError?>(null) }

    LaunchedEffect(Unit) {
        viewModel.setEvent(SpecialityContract.Event.GetSpecialities)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is SpecialityContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.error.toString())
                }

                is SpecialityContract.Effect.ShowSuccessMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is SpecialityContract.Effect.ShowAddDialog -> {
                    showAddDialog = effect.show
                    specialities.refresh()
                    viewModel.setEvent(SpecialityContract.Event.GetSpecialities)
                }
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
            AddButton(
                onClick = { viewModel.setEvent(SpecialityContract.Event.ShowAddDialog(show = true)) },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    count = specialities.itemCount,
                    key = specialities.itemKey { it.id }
                ) { index ->
                    val speciality = specialities[index]
                    if (speciality != null) {
                        InfoItem(
                            infoName = speciality.name.toString(),
                            expanded = false,
                            onExpandedChange = {},
                            infoSecondaryText = "",
                            infoDescription = ""
                        )
                    }
                }

                pagingLoadStateIndicator(
                    lazyPagingItems = specialities,
                    emptyListMessage = "Специальности не найдены"
                )
            }

            showErrorDialog?.let { error ->
                ErrorDialog(
                    title = "Ошибка",
                    message = error.toString(),
                    onDismiss = { showErrorDialog = null },
                    onCopy = { }
                )
            }

            if (showAddDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showAddDialog = false
                        newSpecialityName = ""
                        viewModel.setEvent(SpecialityContract.Event.ShowAddDialog(false))
                        nameError = null
                    },
                    title = { Text("Добавить новую специальность") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newSpecialityName,
                                onValueChange = {
                                    newSpecialityName = it
                                    if (nameError != null) nameError = null
                                },
                                label = { Text("Название специальности") },
                                singleLine = true,
                                isError = nameError != null,
                                supportingText = {
                                    if (nameError != null) {
                                        Text(
                                            text = nameError!!,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newSpecialityName.isBlank()) {
                                    nameError = "Название специальности не может быть пустым"
                                } else {
                                    nameError = null
                                    val specialityRequest = SpecialityCreateRequest(
                                        name = newSpecialityName.trim()
                                    )
                                    viewModel.setEvent(SpecialityContract.Event.AddSpeciality(specialityRequest))
                                }
                            }
                        ) {
                            Text("Добавить")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showAddDialog = false
                                newSpecialityName = ""
                                viewModel.setEvent(SpecialityContract.Event.ShowAddDialog(false))
                                nameError = null
                            }
                        ) {
                            Text("Отмена")
                        }
                    }
                )
            }
        }
    }
}