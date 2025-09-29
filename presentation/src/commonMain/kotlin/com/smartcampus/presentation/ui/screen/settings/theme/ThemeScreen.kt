package com.smartcampus.presentation.ui.screen.settings.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.Theme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ThemeScreen(
    viewModel: ThemeViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val currentTheme = state.theme

    // Загружаем текущую тему при первом запуске экрана
    LaunchedEffect(key1 = Unit) {
        viewModel.setEvent(ThemeContract.Event.GetTheme)
    }

    // Список опций для RadioGroup
    val themeOptions = listOf(
        Theme.SYSTEM to "По умолчанию",
        Theme.LIGHT to "Светлый",
        Theme.DARK to "Темный"
    )

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Выберите тему приложения:",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (currentTheme == null) {
            // Можно показать индикатор загрузки, пока тема не загружена
            Text("Загрузка темы...")
        } else {
            themeOptions.forEach { (themeValue, label) ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (currentTheme == themeValue),
                            onClick = { viewModel.setEvent(ThemeContract.Event.SetTheme(themeValue)) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (currentTheme == themeValue),
                        onClick = null // onClick обрабатывается в Modifier.selectable для всей строки
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
        // Здесь можно добавить другие настройки, если они есть
    }
}
