package com.smartcampus.presentation.ui.screen.security.permission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.smartcampus.crm.domain.models.security.Permission
import com.smartcampus.crm.domain.models.security.PermissionRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.button.AddButton
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.form.LoadingIndicatorDialog
import com.smartcampus.presentation.core.components.text.InfoItem
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PermissionScreen(
    viewModel: PermissionViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val permissions: LazyPagingItems<Permission> = uiState.permissions.collectAsLazyPagingItems()

    var newPermissionName by remember { mutableStateOf("") }
    var newPermissionDescription by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    var expandedStates by remember { mutableStateOf<Map<Int, Boolean>>(emptyMap()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showErrorDialog by remember { mutableStateOf<NetworkError?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PermissionContract.Effect.ShowError -> {
                    showErrorDialog = effect.error
                }

                is PermissionContract.Effect.ShowSuccessMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                    permissions.refresh()
                    newPermissionName = ""
                    newPermissionDescription = ""
                    nameError = null
                }

                is PermissionContract.Effect.ShowAddDialog -> {
                    showAddDialog = effect.show
                }
            }
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (permissions.itemCount == 0 && uiState.isLoading) {
                LoadingIndicatorDialog(isLoading = true)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = permissions.itemCount,
                        key = permissions.itemKey { it.id }
                    ) { index ->
                        val permission = permissions[index]
                        if (permission != null) {
                            InfoItem(
                                infoName = permission.name,
                                infoDescription = permission.description,
                                expanded = expandedStates[permission.id] ?: false,
                                onExpandedChange = { newValue ->
                                    expandedStates = expandedStates.toMutableMap().also {
                                        it[permission.id] = newValue
                                    }
                                },
                                onDeleteClick = {
                                    viewModel.setEvent(
                                        PermissionContract.Event.DeletePermission(
                                            permission.id
                                        )
                                    )
                                }
                            )
                        }
                    }

                    pagingLoadStateIndicator(
                        permissions,
                        addButtonEnabled = true,
                    ) {
                        AddButton(
                            onClick = {
                                viewModel.setEvent(PermissionContract.Event.ShowAddDialog(true))
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }
                }
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
                        viewModel.setEvent(PermissionContract.Event.ShowAddDialog(false))
                        newPermissionName = ""
                        newPermissionDescription = ""
                        nameError = null
                    },
                    title = { Text("Добавить новый доступ") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newPermissionName,
                                onValueChange = {
                                    newPermissionName = it
                                    if (nameError != null) nameError = null
                                },
                                label = { Text("Название доступа") },
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
                                value = newPermissionDescription,
                                onValueChange = { newPermissionDescription = it },
                                label = { Text("Описание доступа") },
                                modifier = Modifier.heightIn(min = 80.dp)
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                if (newPermissionName.isBlank()) {
                                    nameError = "Название доступа не может быть пустым"
                                } else {
                                    nameError = null
                                    val permissionRequest = PermissionRequest(
                                        name = newPermissionName.trim(),
                                        description = newPermissionDescription.trim()
                                    )
                                    viewModel.setEvent(
                                        PermissionContract.Event.AddPermission(
                                            permissionRequest
                                        )
                                    )
                                }
                            }
                        ) {
                            Text("Добавить")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                viewModel.setEvent(PermissionContract.Event.ShowAddDialog(false))
                                newPermissionName = ""
                                newPermissionDescription = ""
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