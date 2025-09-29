package com.smartcampus.presentation.ui.widgets.groupDropdown

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
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GroupDropdown(
    initialSelection: GroupDto?,
    onGroupSelected: (GroupDto) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    viewModel: GroupDropdownViewModel = koinViewModel(),
) {

    val state = viewModel.uiState.collectAsState()
    val groups = state.value.groups.collectAsLazyPagingItems()
    val dropDownIcon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val selectedGroup = state.value.selectedGroup

    LaunchedEffect(initialSelection) {
        viewModel.setEvent(GroupDropdownContract.Event.SelectGroup(initialSelection))
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedGroup?.name ?: " ",
            onValueChange = { },
            label = { Text(text = "Группа") },
            singleLine = true,
            suffix = {
                Icon(
                    imageVector = dropDownIcon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        if (!expanded) viewModel.setEvent(GroupDropdownContract.Event.LoadGroups)
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
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(groups.itemCount) { index ->
                            groups[index]?.let { group ->
                                Column(
                                    modifier = Modifier.clickable {
                                        onGroupSelected(group)
                                        viewModel.setEvent(GroupDropdownContract.Event.SelectGroup(group))
                                        onExpandedChange(false)
                                    }
                                ) {
                                    Text(
                                        text = "Группа: ${group.name ?: ""}",
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
                                    )
                                    Text(
                                        text = "Специализация: ${group.speciality?.name ?: ""}",
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
                                    )
                                    Text(
                                        text = "Курс: ${group.course ?: ""}",
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp)
                                    )
                                }
                                HorizontalDivider()
                            }
                        }

                        pagingLoadStateIndicator(groups)
                    }
                },
                onClick = { }
            )
        }
    }
}