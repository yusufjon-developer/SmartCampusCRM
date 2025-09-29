package com.smartcampus.presentation.ui.widgets.auditoriumDropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import com.smartcampus.crm.domain.models.AuditoriumDto
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuditoriumDropdown(
    initialSelection: AuditoriumDto?,
    onAuditoriumSelected: (AuditoriumDto) -> Unit,
    isAvailable: Boolean,
    day: String,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    viewModel: AuditoriumDropdownViewModel = koinViewModel(),
) {

    val state = viewModel.uiState.collectAsState()
    val auditoriums = state.value.auditoriums.collectAsLazyPagingItems()
    val dropDownIcon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val selectedAuditorium = state.value.selectedAuditorium

    LaunchedEffect(initialSelection) {
        viewModel.setEvent(AuditoriumDropdownContract.Event.SelectAuditorium(initialSelection))
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = if (selectedAuditorium != null) "${selectedAuditorium.number}. ${selectedAuditorium.type}" else " ",
            onValueChange = { },
            label = { Text(text = "Аудитория") },
            singleLine = true,
            suffix = {
                Icon(
                    imageVector = dropDownIcon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        if (!expanded) viewModel.setEvent(AuditoriumDropdownContract.Event.LoadAuditoriums(isAvailable, day))
                        onExpandedChange(!expanded)
                    }
                )
            },
            modifier = modifier
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onExpandedChange(false)
            },
            modifier = Modifier.height(480.dp).width(300.dp),
        ) {
            DropdownMenuItem(
                text = {
                    LazyColumn(
                        modifier = Modifier.height(480.dp).width(300.dp),
                        contentPadding = PaddingValues(4.dp)
                    ) {
                        items(auditoriums.itemCount) { index ->
                            auditoriums[index]?.let { auditorium ->
                                Column(
                                    modifier = Modifier.clickable {
                                        onAuditoriumSelected(auditorium)
                                        viewModel.setEvent(
                                            AuditoriumDropdownContract.Event.SelectAuditorium(auditorium)
                                        )
                                        onExpandedChange(false)
                                    }
                                ) {
                                    Text(
                                        text = "№ ${auditorium.number ?: ""}",
                                        modifier = Modifier.fillMaxWidth().padding(2.dp)
                                    )
                                    Text(
                                        text = "Тип: ${auditorium.type ?: ""}",
                                        modifier = Modifier.fillMaxWidth().padding(2.dp)
                                    )
                                }
                                HorizontalDivider()
                            }
                        }

                        pagingLoadStateIndicator(auditoriums)
                    }
                },
                onClick = { }
            )
        }
    }
}