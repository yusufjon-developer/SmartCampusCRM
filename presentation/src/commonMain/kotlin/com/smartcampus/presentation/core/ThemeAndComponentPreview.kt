package com.smartcampus.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp

@Composable
fun ThemeAndComponentPreview() {
    val checked = remember { mutableStateOf(true) }
    val switchChecked = remember { mutableStateOf(false) }
    val selectedRadio = remember { mutableStateOf("Option 1") }
    val textFieldValue = remember { mutableStateOf("") }
    val showDialog = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

        Text("Buttons", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {}) { Text("Button") }
            Button(onClick = {}, enabled = false) { Text("Disabled") }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = {}) { Text("Outlined") }
            TextButton(onClick = {}) { Text("Text") }
        }

        Text("Elevated / Filled / Tonal", style = MaterialTheme.typography.headlineSmall)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            ElevatedButton(onClick = {}) { Text("Elevated") }
            FilledTonalButton(onClick = {}) { Text("Tonal") }
            FilledIconButton(onClick = {}) {
                Icon(Icons.Default.Star, contentDescription = null)
            }
        }

        Text("TextFields", style = MaterialTheme.typography.headlineSmall)
        TextField(
            value = textFieldValue.value,
            onValueChange = { textFieldValue.value = it },
            label = { Text("Standard") },
            singleLine = true
        )
        OutlinedTextField(
            value = textFieldValue.value,
            onValueChange = { textFieldValue.value = it },
            label = { Text("Outlined") },
            isError = textFieldValue.value.length > 5,
            trailingIcon = { Icon(Icons.Default.Lock, null, tint = MaterialTheme.colorScheme.primary) }
        )
        OutlinedTextField(
            value = "Disabled",
            onValueChange = {},
            enabled = false,
            label = { Text("Disabled") }
        )

        Text("Checkboxes & Switches", style = MaterialTheme.typography.headlineSmall)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checked.value, onCheckedChange = { checked.value = it })
            Text("Check me")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(checked = switchChecked.value, onCheckedChange = { switchChecked.value = it })
            Text("Switch me")
        }

        Text("Radios", style = MaterialTheme.typography.headlineSmall)
        Column {
            listOf("Option 1", "Option 2").forEach { option ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = selectedRadio.value == option,
                        onClick = { selectedRadio.value = option }
                    )
                    Text(option)
                }
            }
        }

        Text("Progress", style = MaterialTheme.typography.headlineSmall)
        LinearProgressIndicator(
            progress = { 0.5f },
            color = ProgressIndicatorDefaults.linearColor,
            trackColor = ProgressIndicatorDefaults.linearTrackColor,
            strokeCap = ProgressIndicatorDefaults.LinearStrokeCap,
        )
        CircularProgressIndicator()

        Text("Typography", style = MaterialTheme.typography.headlineSmall)
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text("Display Large", style = MaterialTheme.typography.displayLarge)
            Text("Headline Medium", style = MaterialTheme.typography.headlineMedium)
            Text("Body Medium", style = MaterialTheme.typography.bodyMedium)
            Text("Label Small", style = MaterialTheme.typography.labelSmall)
        }

        Text("Colors", style = MaterialTheme.typography.headlineSmall)
        val colors = MaterialTheme.colorScheme
        listOf(
            "Primary" to colors.primary,
            "Secondary" to colors.secondary,
            "Tertiary" to colors.tertiary,
            "Error" to colors.error,
            "Surface" to colors.surface,
            "Background" to colors.background
        ).forEach { (name, color) ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .background(color)
                    .padding(start = 8.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(name, color = colors.onSurface)
            }
        }

        LightThemeColorPreview()

        Text("Dialog", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = { showDialog.value = true }) {
            Text("Show Dialog")
        }

        if (showDialog.value) {
            AlertDialog(
                onDismissRequest = { showDialog.value = false },
                confirmButton = {
                    TextButton(onClick = { showDialog.value = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Sample Dialog") },
                text = { Text("This is a dialog example") }
            )
        }

        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun LightThemeColorPreview() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ColorPreview("primary", MaterialTheme.colorScheme.primary)
        ColorPreview("onPrimary", MaterialTheme.colorScheme.onPrimary)
        ColorPreview("primaryContainer", MaterialTheme.colorScheme.primaryContainer)
        ColorPreview("onPrimaryContainer", MaterialTheme.colorScheme.onPrimaryContainer)
        ColorPreview("inversePrimary", MaterialTheme.colorScheme.inversePrimary)

        ColorPreview("secondary", MaterialTheme.colorScheme.secondary)
        ColorPreview("onSecondary", MaterialTheme.colorScheme.onSecondary)
        ColorPreview("secondaryContainer", MaterialTheme.colorScheme.secondaryContainer)
        ColorPreview("onSecondaryContainer", MaterialTheme.colorScheme.onSecondaryContainer)

        ColorPreview("tertiary", MaterialTheme.colorScheme.tertiary)
        ColorPreview("onTertiary", MaterialTheme.colorScheme.onTertiary)
        ColorPreview("tertiaryContainer", MaterialTheme.colorScheme.tertiaryContainer)
        ColorPreview("onTertiaryContainer", MaterialTheme.colorScheme.onTertiaryContainer)

        ColorPreview("background", MaterialTheme.colorScheme.background)
        ColorPreview("onBackground", MaterialTheme.colorScheme.onBackground)
        ColorPreview("surface", MaterialTheme.colorScheme.surface)
        ColorPreview("onSurface", MaterialTheme.colorScheme.onSurface)
        ColorPreview("surfaceVariant", MaterialTheme.colorScheme.surfaceVariant)
        ColorPreview("onSurfaceVariant", MaterialTheme.colorScheme.onSurfaceVariant)
        ColorPreview("surfaceTint", MaterialTheme.colorScheme.surfaceTint)

        ColorPreview("inverseSurface", MaterialTheme.colorScheme.inverseSurface)
        ColorPreview("inverseOnSurface", MaterialTheme.colorScheme.inverseOnSurface)

        ColorPreview("error", MaterialTheme.colorScheme.error)
        ColorPreview("onError", MaterialTheme.colorScheme.onError)
        ColorPreview("errorContainer", MaterialTheme.colorScheme.errorContainer)
        ColorPreview("onErrorContainer", MaterialTheme.colorScheme.onErrorContainer)

        ColorPreview("outline", MaterialTheme.colorScheme.outline)
        ColorPreview("outlineVariant", MaterialTheme.colorScheme.outlineVariant)
        ColorPreview("scrim", MaterialTheme.colorScheme.scrim)

        ColorPreview("surfaceBright", MaterialTheme.colorScheme.surfaceBright)
        ColorPreview("surfaceContainer", MaterialTheme.colorScheme.surfaceContainer)
        ColorPreview("surfaceContainerHigh", MaterialTheme.colorScheme.surfaceContainerHigh)
        ColorPreview(
            "surfaceContainerHighest",
            MaterialTheme.colorScheme.surfaceContainerHighest
        )
        ColorPreview("surfaceContainerLow", MaterialTheme.colorScheme.surfaceContainerLow)
        ColorPreview("surfaceContainerLowest", MaterialTheme.colorScheme.surfaceContainerLowest)
        ColorPreview("surfaceDim", MaterialTheme.colorScheme.surfaceDim)
    }
}

@Composable
private fun ColorPreview(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = if (color.luminance() < 0.5f) Color.White else Color.Black
        )
    }
}