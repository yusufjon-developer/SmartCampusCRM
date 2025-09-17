package com.smartcampus.presentation.ui.screen.home.auditorium

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.paging.compose.itemKey
import app.cash.paging.compose.collectAsLazyPagingItems
import com.smartcampus.crm.domain.models.AuditoriumCreateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.button.AddButton
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.text.InfoItem
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuditoriumScreen(
    viewModel: AuditoriumViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val auditorium = uiState.auditoriums.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    var newAuditoriumNumber by remember { mutableStateOf("") }
    var newAuditoriumType by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    var showAddDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf<NetworkError?>(null) }

    LaunchedEffect(Unit) {
        viewModel.setEvent(AuditoriumContract.Event.GetAuditoriums)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuditoriumContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.error.toString())
                }

                is AuditoriumContract.Effect.ShowSuccessMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is AuditoriumContract.Effect.ShowAddDialog -> {
                    showAddDialog = effect.show
                    auditorium.refresh()
                    viewModel.setEvent(AuditoriumContract.Event.GetAuditoriums)
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
                onClick = { viewModel.setEvent(AuditoriumContract.Event.ShowAddDialog(show = true)) },
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
                    count = auditorium.itemCount,
                    key = auditorium.itemKey { it.id }
                ) { index ->
                    val auditorium = auditorium[index]
                    if (auditorium != null) {
                        InfoItem(
                            modifier = Modifier.clickable {

                            },
                            infoName = auditorium.number.toString(),
                            infoDescription = auditorium.type.toString(),
                            expanded = false,
                            onExpandedChange = {}
                        )
                    }
                }

                pagingLoadStateIndicator(
                    lazyPagingItems = auditorium,
                    emptyListMessage = "Аудитории не найдены"
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
                        // Действие при закрытии диалога (например, свайпом или кнопкой "Назад")
                        showAddDialog = false
                        newAuditoriumNumber = "" // Очистка полей
                        viewModel.setEvent(AuditoriumContract.Event.ShowAddDialog(false))
                        newAuditoriumType = ""
                        nameError = null
                    },
                    title = { Text("Добавить новую аудиторию") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newAuditoriumNumber,
                                onValueChange = {
                                    newAuditoriumNumber = it
                                    if (nameError != null) nameError = null
                                },
                                label = { Text("Название аудитории") },
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

                            OutlinedTextField(
                                value = newAuditoriumType,
                                onValueChange = { newAuditoriumType = it },
                                label = { Text("Тип аудитории") },
                                modifier = Modifier.heightIn(min = 80.dp)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newAuditoriumNumber.isBlank()) {
                                    nameError = "Название аудитории не может быть пустым"
                                } else {
                                    nameError = null
                                    val auditoriumRequest = AuditoriumCreateRequest(
                                        number = newAuditoriumNumber.trim(),
                                        type = newAuditoriumType.trim()
                                    )
                                    viewModel.setEvent(AuditoriumContract.Event.AddAuditorium(auditoriumRequest))
                                    // Не закрываем диалог здесь, ждем ответа от ViewModel (успех/ошибка)
                                    // Диалог закроется в LaunchedEffect при RoleContract.Effect.ShowSuccessMessage
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
                                newAuditoriumNumber = "" // Очистка полей
                                viewModel.setEvent(AuditoriumContract.Event.ShowAddDialog(false))
                                newAuditoriumType = ""
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