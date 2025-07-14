package com.smartcampus.presentationCore.components.inputs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.smartcampus.presentationCore.theme.lightTheme

@Composable
fun PasswordField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    hasError: Boolean = false,
    label: String = "Пароль",
    placeholder: String = "Введите пароль"
) {
    var visibility by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
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
                imageVector = Icons.Outlined.Lock,
                contentDescription = null,
                Modifier.padding(horizontal = 4.dp)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = if (visibility) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = null,
                modifier = Modifier.clickable { visibility = !visibility }
            )
        },
        isError = hasError,
        visualTransformation = if (visibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        ),
        singleLine = true,
        colors = textFieldColors
    )
}