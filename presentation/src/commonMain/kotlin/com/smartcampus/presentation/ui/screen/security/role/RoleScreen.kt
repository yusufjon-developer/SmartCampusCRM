package com.smartcampus.presentation.ui.screen.security.role

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import com.smartcampus.crm.domain.models.security.Role
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.form.LoadingIndicatorDialog
import com.smartcampus.presentation.core.components.text.InfoItem
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoleScreen(
    viewModel: RoleViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val roles: LazyPagingItems<Role> = uiState.roles.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showErrorDialog by remember { mutableStateOf<NetworkError?>(null) }


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RoleContract.Effect.ShowError -> {
                    showErrorDialog = effect.error
                }
                is RoleContract.Effect.ShowSuccessMessage -> {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = effect.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                    roles.refresh()
                }
            }
        }
    }

    var expandedStates by remember { mutableStateOf<Map<Int, Boolean>>(emptyMap()) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (roles.itemCount == 0 && uiState.isLoading) {
                LoadingIndicatorDialog(isLoading = true)
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = roles.itemCount,
                        key = roles.itemKey { it.id }
                    ) { index ->
                        val role = roles[index]
                        if (role != null) {
                            InfoItem(
                                infoName = role.name,
                                infoDescription = role.description,
                                expanded = expandedStates[role.id] ?: false,
                                onExpandedChange = { newValue ->
                                    expandedStates = expandedStates.toMutableMap().also {
                                        it[role.id] = newValue
                                    }
                                },
                                onDeleteClick = {
                                    viewModel.setEvent(RoleContract.Event.DeleteRole(role.id))
                                }
                            )
                        }
                    }

                    pagingLoadStateIndicator(roles)
                }
            }

            showErrorDialog?.let { error ->
                ErrorDialog(
                    title = "Ошибка",
                    message = error.toString(),
                    onDismiss = { showErrorDialog = null },
                    onCopy = {  }
                )
            }
        }
    }
}