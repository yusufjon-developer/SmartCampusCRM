package com.smartcampus.presentationCore.components.inputs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.presentationCore.theme.lightTheme

@Composable
fun UserField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    label: String = "Имя пользователя",
    placeholder: String = "Введите имя пользователья"
) {
    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = lightTheme.onSurface,
        unfocusedTextColor = lightTheme.onSurface,
        disabledTextColor = lightTheme.onSurfaceVariant,
        errorTextColor = lightTheme.onError,

        focusedContainerColor = lightTheme.surface,
        unfocusedContainerColor = lightTheme.surface,
        disabledContainerColor = lightTheme.surfaceVariant,
        errorContainerColor = lightTheme.errorContainer,

        cursorColor = lightTheme.primary,
        errorCursorColor = lightTheme.error,

        focusedIndicatorColor = lightTheme.primary,
        unfocusedIndicatorColor = lightTheme.outline,
        disabledIndicatorColor = lightTheme.outlineVariant,
        errorIndicatorColor = lightTheme.error,

        focusedLeadingIconColor = lightTheme.primary,
        unfocusedLeadingIconColor = lightTheme.onSurfaceVariant,
        disabledLeadingIconColor = lightTheme.outline,
        errorLeadingIconColor = lightTheme.error,

        focusedTrailingIconColor = lightTheme.primary,
        unfocusedTrailingIconColor = lightTheme.onSurfaceVariant,
        disabledTrailingIconColor = lightTheme.outline,
        errorTrailingIconColor = lightTheme.error,

        focusedLabelColor = lightTheme.primary,
        unfocusedLabelColor = lightTheme.onSurfaceVariant,
        disabledLabelColor = lightTheme.outline,
        errorLabelColor = lightTheme.error,

        focusedPlaceholderColor = lightTheme.onSurfaceVariant,
        unfocusedPlaceholderColor = lightTheme.onSurfaceVariant,
        disabledPlaceholderColor = lightTheme.outline,
        errorPlaceholderColor = lightTheme.error,

        focusedSupportingTextColor = lightTheme.onSurfaceVariant,
        unfocusedSupportingTextColor = lightTheme.onSurfaceVariant,
        disabledSupportingTextColor = lightTheme.outlineVariant,
        errorSupportingTextColor = lightTheme.error,

        focusedPrefixColor = lightTheme.primary,
        unfocusedPrefixColor = lightTheme.onSurface,
        disabledPrefixColor = lightTheme.outline,
        errorPrefixColor = lightTheme.error,

        focusedSuffixColor = lightTheme.primary,
        unfocusedSuffixColor = lightTheme.onSurface,
        disabledSuffixColor = lightTheme.outline,
        errorSuffixColor = lightTheme.error
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = true,
        readOnly = false,
        textStyle = MaterialTheme.typography.bodyMedium,
        label = {
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = lightTheme.onBackground
            )
        },
        placeholder = {
            Text(
                text = placeholder,
                style = MaterialTheme.typography.bodyMedium,
                color = lightTheme.onBackground
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Outlined.Person,
                contentDescription = null,
                Modifier.padding(horizontal = 4.dp)
            )
        },
        isError = hasError,
        singleLine = true,
        colors = textFieldColors
    )
}
