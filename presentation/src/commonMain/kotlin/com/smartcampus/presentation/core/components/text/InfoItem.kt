package com.smartcampus.presentationCore.components.texts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun InfoItem(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(end = 8.dp)
                .weight(0.5f)
                .alignByBaseline(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Text(
            text = value,
            modifier = Modifier
                .weight(0.5f)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .alignByBaseline(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold

        )
    }
}