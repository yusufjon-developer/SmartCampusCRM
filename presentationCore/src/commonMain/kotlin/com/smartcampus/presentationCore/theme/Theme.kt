package com.smartcampus.presentationCore.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SmartCampusTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) DarkColors else LightColors

    MaterialTheme(
        typography = SmartCampusTypography(),
        colorScheme = colorScheme,
        shapes = AppShapes
    ) {
        content()
    }
}