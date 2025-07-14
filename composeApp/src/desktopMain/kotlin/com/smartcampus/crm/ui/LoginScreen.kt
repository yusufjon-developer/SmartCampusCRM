package com.smartcampus.crm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smartcampus.presentationCore.components.inputs.PasswordField
import com.smartcampus.presentationCore.components.inputs.UserField
import com.smartcampus.presentationCore.theme.lightTheme

@Composable
fun LoginScreen(
    navigateToMain: (String) -> Unit,
    navigateToRegistration: (String) -> Unit
) {

    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

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
            colors = ButtonColors(
                containerColor = Color(0xFF233255),
                contentColor = Color(0xFFFDFDF5),
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.Gray
            ),
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
            color = lightTheme.onBackground ,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        UserField(
            value = username.value,
            onValueChange = { newUsername ->
                username.value = newUsername
            },
            modifier = Modifier.width(300.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordField(
            value = password.value,
            onValueChange = { newPassword ->
                password.value = newPassword
            },
            modifier = Modifier.width(300.dp),
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                navigateToMain("Успешный вход!")
            },
            colors = ButtonColors(
                containerColor = Color(0xFF233255),
                contentColor = Color(0xFFFDFDF5),
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.Gray
            )
        ) {
            Text("Войти", color = Color.White)
        }
    }
}