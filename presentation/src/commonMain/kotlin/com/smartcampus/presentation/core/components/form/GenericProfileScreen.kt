package com.smartcampus.presentation.core.components.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GenericProfileScreen(
    initialReadOnly: Boolean = true,
    onToggleEdit: (isEditing: Boolean) -> Unit,
    topContent: @Composable () -> Unit = {},
    bodyContent: @Composable (isEditing: Boolean) -> Unit
) {
    val readOnlyState = remember { mutableStateOf(initialReadOnly) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.padding(16.dp)) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    topContent()

                    Spacer(modifier = Modifier.height(12.dp))

                    bodyContent(!readOnlyState.value)
                }
            }

            Button(
                onClick = {
                    if (readOnlyState.value) {
                        readOnlyState.value = false
                        onToggleEdit(true)
                    } else {
                        readOnlyState.value = true
                        onToggleEdit(false)
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                shape = RoundedCornerShape(9999.dp)
            ) {
                Icon(
                    imageVector = if (readOnlyState.value) Icons.Filled.Edit else Icons.Filled.Save,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (readOnlyState.value) "Изменить" else "Сохранить")
            }
        }
    }
}
