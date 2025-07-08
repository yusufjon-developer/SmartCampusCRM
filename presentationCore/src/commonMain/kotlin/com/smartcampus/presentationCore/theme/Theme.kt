package com.smartcampus.presentationCore.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SmartCampusTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        typography = SmartCampusTypography()
    ) {
        content()
    }
}