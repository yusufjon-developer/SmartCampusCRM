package com.smartcampus.presentation.core.components.form

import androidx.compose.runtime.MutableState

/**
 * Поле для чтения (read-only view).
 */
data class ReadOnlyField(
    val label: String,
    val value: String
)

/**
 * Поле для редактирования. Значение и callback передаются извне (hoisted state).
 */
data class EditableField(
    val label: String,
    val valueState: MutableState<String>,
    val singleLine: Boolean = true
)