package com.smartcampus.presentation.ui.screen.security.userPermission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import com.smartcampus.crm.domain.models.security.UpdateUserPermissions
import com.smartcampus.crm.domain.models.security.UserDevice
import com.smartcampus.crm.domain.utils.NetworkError
import org.koin.compose.viewmodel.koinViewModel
import java.util.logging.Logger

@Composable
fun UserPermissionScreen(
    userId: Int,
    viewModel: UserPermissionViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val user = state.user
    val grantedPermission = remember { mutableStateListOf<Int>() }
    val revokedPermission = remember { mutableStateListOf<Int>() }
    val isActive = remember { mutableStateOf(false) }
    val devices = remember { mutableStateListOf<UserDevice>() }


    val networkError = remember { mutableStateOf<NetworkError?>(null) }

    LaunchedEffect(Unit) {
        viewModel.setEvent(UserPermissionContract.Event.LoadUser(userId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is UserPermissionContract.Effect.ShowError -> {
                    networkError.value = effect.error
                }

                is UserPermissionContract.Effect.ShowSuccessMessage -> {
                    Logger.getLogger("UserPermissionScreen").info(effect.message)
                }
            }
        }
    }

    LaunchedEffect(state.user) {
        isActive.value = state.user.isActive

        devices.clear()
        devices.addAll(state.user.userDevices)
    }

    Column(
        modifier = Modifier.fillMaxWidth().verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = user.username,
            style = MaterialTheme.typography.displayMedium,
        )

//        Text(
//            text = user.username,
//            style = MaterialTheme.typography.titleLarge
//        )

        Spacer(modifier = Modifier.padding(16.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = isActive.value,
                onCheckedChange = { check -> isActive.value = check },
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            )
            Text(
                text = "Статус: ${if (isActive.value) "Активен" else "Неактивен"}",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            "Устройства",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        devices.forEach { device ->
            val checked = device.isApprove
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { check ->
                        val index = devices.indexOfFirst { it == device }
                        if (index >= 0) {
                            devices[index] = devices[index].copy(isApprove = check)
                        }
                    },
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                )
                Text(
                    text = device.registeredAt,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(end = 16.dp, top = 8.dp, bottom = 8.dp)
                )
            }
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            "Разрешение",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        user.permissions.forEach { permission ->
            val checked = when (permission.id) {
                in grantedPermission -> true
                in revokedPermission -> false
                else -> permission.hasPermission
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
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
                    UserPermissionContract.Event.UpdateUserPermission(
                        UpdateUserPermissions(
                            isActive = isActive.value,
                            userDevices = devices.filter { it.isApprove }.map { it -> it.id }.toSet(),
                            permissions = UpdatePermissionStatus(
                                grantedPermission.toSet(),
                                revokedPermission.toSet()
                            )
                        )
                    )
                )
            },
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.tertiary.copy(alpha = 0.5f)),
            modifier = Modifier.padding(16.dp).fillMaxWidth()
        ) {
            Text(text = "Сохранить", color = MaterialTheme.colorScheme.tertiary)
        }

    }
}