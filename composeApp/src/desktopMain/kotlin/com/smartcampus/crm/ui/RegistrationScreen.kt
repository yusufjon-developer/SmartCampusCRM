package com.smartcampus.crm.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationScreen(
    navigateToLogin: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End
    ) {
        Button(
            onClick = {
                navigateToLogin("Входа")
            },
            colors = ButtonColors(
                containerColor = Color(0xFF233255),
                contentColor = Color(0xFFFDFDF5),
                disabledContainerColor = Color.LightGray,
                disabledContentColor = Color.Gray
            ),
        ) {
            Text("Вход", color = Color.White)
        }
    }
}