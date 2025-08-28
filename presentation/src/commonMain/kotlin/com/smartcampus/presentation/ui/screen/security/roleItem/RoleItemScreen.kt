package com.smartcampus.presentation.ui.screen.security.roleItem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf


@Composable
fun RoleItemScreen(
    roleId: Int,
    viewModel: RoleItemViewModel = koinViewModel(parameters = { parametersOf(roleId) })
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val role = state.role
    val grantedPermission = remember { mutableStateListOf<Int>() }
    val revokedPermission = remember { mutableStateListOf<Int>() }


    LaunchedEffect(Unit) {
        viewModel.setEvent(RoleItemContract.Event.LoadRole(roleId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RoleItemContract.Effect.ShowError -> {}
                is RoleItemContract.Effect.ShowSuccessMessage -> {}
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = role.name,
            style = MaterialTheme.typography.displayMedium,
        )

        Text(
            text = role.description,
            style = MaterialTheme.typography.titleLarge
        )

        role.permissions.forEach { permission ->
            val checked = when (permission.id) {
                in grantedPermission -> true
                in revokedPermission -> false
                else -> permission.hasPermission
            }
            Row {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { check ->
                        if (check) {
                            grantedPermission.add(permission.id)
                            revokedPermission.remove(permission.id)
                        } else {
                            grantedPermission.remove(permission.id)
                            revokedPermission.add(permission.id)
                        }
                    },
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
                Text(
                    text = permission.name,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }

        OutlinedButton(
            onClick = {
                viewModel.setEvent(
                    RoleItemContract.Event.UpdateRolePermission(
                        UpdatePermissionStatus(
                            grantedPermission.toSet(),
                            revokedPermission.toSet()
                        )
                    )
                )
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(text = "Сохранить", color = MaterialTheme.colorScheme.tertiary)
        }

        OutlinedButton(
            onClick = {
                viewModel.setEvent(
                    RoleItemContract.Event.DeleteRole(roleId)
                )
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(text = "Удалить", color = MaterialTheme.colorScheme.error)
        }
    }
}