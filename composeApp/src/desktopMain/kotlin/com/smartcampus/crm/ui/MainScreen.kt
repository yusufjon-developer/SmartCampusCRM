package com.smartcampus.crm.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.smartcampus.presentationCore.theme.lightTheme

@Composable
fun MainScreen(
    test: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = lightTheme.onBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = test,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium
        )
    }
}