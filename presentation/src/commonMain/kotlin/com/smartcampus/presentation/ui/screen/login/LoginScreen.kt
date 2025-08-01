package com.smartcampus.presentation.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.smartcampus.crm.domain.models.UserRequest
import com.smartcampus.presentation.core.components.inputs.PasswordField
import com.smartcampus.presentation.core.components.inputs.UserField
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LoginScreen(
    navigateToMain: (String) -> Unit,
    navigateToRegistration: (String) -> Unit,
    viewModel: LoginViewModel = koinViewModel()
) {

    var user by remember { mutableStateOf(UserRequest("", "")) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is LoginContract.Effect.Error -> {
                    navigateToMain("Ошибка входа!")
                }
                LoginContract.Effect.Success -> {
                    navigateToMain("Успешный вход!")
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = {
                navigateToRegistration("Окно зарегистрирования")
            },
        ) {
            Text("Зарегистрироватся", color = Color.White)
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
            value = user.username,
            onValueChange = { newUsername ->
                user = user.copy(username = newUsername)
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