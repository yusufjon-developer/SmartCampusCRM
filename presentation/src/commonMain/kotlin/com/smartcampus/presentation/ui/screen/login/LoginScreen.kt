package com.smartcampus.presentation.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.form.LoadingIndicatorDialog
import com.smartcampus.presentation.core.components.form.PasswordField
import com.smartcampus.presentation.core.components.form.UserField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var showError by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginContract.Effect.Error -> {
                    showError = true
                }

                LoginContract.Effect.Success -> {
                    onLoginSuccess()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Вход",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        UserField(
            value = state.request.email,
            onValueChange = { newEmail ->
                viewModel.setEvent(LoginContract.Event.EmailChanged(newEmail))
            },
            modifier = Modifier.width(300.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = state.request.password,
            onValueChange = { newPassword ->
                viewModel.setEvent(LoginContract.Event.PasswordChanged(newPassword))
            },
            modifier = Modifier.width(300.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.setEvent(LoginContract.Event.Login)
            }
        ) {
            Text("Войти", color = Color.White)
        }

        LoadingIndicatorDialog(isLoading = state.isLoading)
        if (showError) {
            ErrorDialog(
                title = state.error!!::class.simpleName!!,
                message = state.error!!,
                onDismiss = { showError = false }
            )
        }
    }
}