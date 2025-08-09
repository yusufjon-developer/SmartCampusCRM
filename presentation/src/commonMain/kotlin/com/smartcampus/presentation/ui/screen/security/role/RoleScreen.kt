package com.smartcampus.presentation.ui.screen.security.role

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.presentation.core.components.text.InfoItem
import com.smartcampus.presentation.ui.screen.settings.theme.ThemeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoleScreen(
    viewModel: ThemeViewModel = koinViewModel()
) {
    val roles = listOf(
        "Администратор" to "Имеет полный доступ ко всем функциям системы",
        "Охранник" to "Отвечает за мониторинг камер и реагирование на тревоги",
        "Посетитель" to "Может просматривать информацию и посещать зоны с разрешения",
        "Техник" to "Проводит обслуживание оборудования и датчиков"
    )

    var expandedStates by remember { mutableStateOf(List(roles.size) { false }) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(roles.size) { index ->
            val (name, description) = roles[index]
            InfoItem(
                infoName = name,
                infoDescription = description,
                expanded = expandedStates[index],
                onExpandedChange = { newValue ->
                    expandedStates = expandedStates.toMutableList().also {
                        it[index] = newValue
                    }
                },
                onDeleteClick = {
                    println("Удалить роль: $name")
                }
            )
        }
    }
}