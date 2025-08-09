package com.smartcampus.presentation.core.components.form

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.utils.NetworkError

@Composable
fun ErrorDialog(
    title: String,
    message: Any,
    onDismiss: () -> Unit,
    onCopy: ((String) -> Unit) = {  },
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val errorMessageString = remember(message) {
        when (message) {
            is NetworkError -> message.toString()
            is String -> message
            else -> message.toString()
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        },
        text = {
            Text(
                text = errorMessageString,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer,
                overflow = TextOverflow.Ellipsis
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Text("ОК")
            }
        },
        dismissButton = {

            Button(
                onClick = {
                    clipboardManager.setText(AnnotatedString(errorMessageString))
                    onCopy(errorMessageString)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                )
            ) {
                Text("Копировать")
            }
        },
        containerColor = MaterialTheme.colorScheme.errorContainer,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
    )
}