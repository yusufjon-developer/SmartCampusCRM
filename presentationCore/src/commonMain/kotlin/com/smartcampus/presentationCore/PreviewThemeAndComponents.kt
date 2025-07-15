package com.smartcampus.presentationCore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PreviewThemeAndComponents() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Привет, мир!")
        TextField(
            value = "Введите текст",
            onValueChange = {  }
        )
        OutlinedTextField(
            value = "Введите текст",
            onValueChange = {  }
        )
        Button(onClick = {  }) {  }
        OutlinedButton(onClick = {  }) {  }
    }
}