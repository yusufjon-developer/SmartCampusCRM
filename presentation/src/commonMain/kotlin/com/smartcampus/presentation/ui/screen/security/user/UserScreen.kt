package com.smartcampus.presentation.ui.screen.security.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.smartcampus.crm.domain.models.security.User
import com.smartcampus.crm.domain.models.security.UserRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.button.AddButton
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.form.LoadingIndicatorDialog
import com.smartcampus.presentation.core.components.text.InfoItem
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel
import java.util.logging.Logger

@Composable
fun UserScreen(
    navigateToUserPermissionScreen: (Int) -> Unit,
    viewModel: UserViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val user: LazyPagingItems<User> = uiState.users.collectAsLazyPagingItems()

    var newUserName by remember { mutableStateOf("") }
    var nameError by remember { mutableStateOf<String?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }
    var showErrorDialog by remember { mutableStateOf<NetworkError?>(null) }
    var showAddDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UserContract.Effect.ShowError -> {
                    showErrorDialog = effect.error
                }
                is UserContract.Effect.NavigateToUserPermissionScreen -> {
                    navigateToUserPermissionScreen(effect.userId)
                }
                is UserContract.Effect.ShowAddDialog -> {
                    showAddDialog = effect.show
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (user.itemCount == 0 && uiState.isLoading) {
                LoadingIndicatorDialog(isLoading = true)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = user.itemCount,
                        key = user.itemKey { it.id }
                    ) { index ->
                        Logger.getAnonymousLogger().info("user.itemCount: ${user.itemCount}")
                        val user = user[index]
                        Logger.getAnonymousLogger().info("user: $user")
                        if (user != null) {
                            InfoItem(
                                infoName = user.name,
                                infoDescription = "",
                                expanded = false,
                                onExpandedChange = {
                                    navigateToUserPermissionScreen(user.id)
                                }
                            )
                        }
                    }

                    pagingLoadStateIndicator(
                        user,
                        addButtonEnabled = true,
                    ) {
                        AddButton(
                            onClick = {
                                viewModel.setEvent(UserContract.Event.ShowAddDialog(true))
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
                        // Действие при закрытии диалога (например, свайпом или кнопкой "Назад")
                        showAddDialog = false
                        newUserName = "" // Очистка полей
                        viewModel.setEvent(UserContract.Event.ShowAddDialog(false))
                        newUserName = ""
                        nameError = null
                    },
                    title = { Text("Добавить нового пользователя") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                value = newUserName,
                                onValueChange = {
                                    newUserName = it
                                    if (nameError != null) nameError = null
                                },
                                label = { Text("Название пользователя") },
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
                                if (newUserName.isBlank()) {
                                    nameError = "Название пользователя не может быть пустым"
                                } else {
                                    nameError = null
                                    val userRequest = UserRequest(
                                        name = newUserName.trim()
                                    )
                                    viewModel.setEvent(UserContract.Event.AddUser(userRequest))
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
                                newUserName = "" // Очистка полей
                                viewModel.setEvent(UserContract.Event.ShowAddDialog(false))
                                newUserName = ""
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