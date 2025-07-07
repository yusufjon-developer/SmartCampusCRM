package com.smartcampus.presentationCore.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WindowControlButton(
    onClick: () -> Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(8.dp),
    content: @Composable () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonColors(
            containerColor = Color(0xFF233255),
            contentColor = Color(0xFFFDFDF5),
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp),
        border = null,
        contentPadding = contentPadding
    ) {
        content()
    }
}