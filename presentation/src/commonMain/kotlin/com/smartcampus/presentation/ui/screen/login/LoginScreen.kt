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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.LoginRequest
import com.smartcampus.presentation.core.components.form.PasswordField
import com.smartcampus.presentation.core.components.form.UserField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {

    var user by remember { mutableStateOf(LoginRequest("", "", "device-uuid-for-teacher01")) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginContract.Effect.Error -> {

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
            value = user.email,
            onValueChange = { newUsername ->
                user = user.copy(email = newUsername)
            },
            modifier = Modifier.width(300.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = user.password,
            onValueChange = { newPassword ->
                user = user.copy(password = newPassword)
            },
            modifier = Modifier.width(300.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.setEvent(LoginContract.Event.Login(user))
            }
        ) {
            Text("Войти", color = Color.White)
        }
    }
}