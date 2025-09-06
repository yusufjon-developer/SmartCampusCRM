package com.smartcampus.presentation.ui.widgets.groupDropdown

import androidx.compose.foundation.clickable
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
    selectGroup: GroupDto?,
    onGroupSelected: (GroupDto) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GroupDropdownViewModel = koinViewModel(),
) {

    val state = viewModel.uiState.collectAsState()
    val groups = state.value.groups.collectAsLazyPagingItems()
    val expanded = state.value.expanded
    val dropDownIcon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val selectedGroup = state.value.selectedGroup

    LaunchedEffect(Unit) {
        viewModel.setEvent(GroupDropdownContract.Event.LoadGroups)
        selectGroup?.let { viewModel.setEvent(GroupDropdownContract.Event.SelectGroup(it)) }
    }

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
                    viewModel.setEvent(GroupDropdownContract.Event.ToggleDropdown(expanded))
                }
            )
        },
        modifier = modifier
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            viewModel.setEvent(GroupDropdownContract.Event.ToggleDropdown(expanded))
        },
        modifier = modifier.height(300.dp).width(300.dp),

        ) {
        DropdownMenuItem(
            text = {
                LazyColumn(
                    modifier = modifier.height(300.dp).width(300.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(groups.itemCount) {
                        Column(
                            modifier = Modifier.clickable {
                                onGroupSelected(groups[it]!!)
                                viewModel.setEvent(GroupDropdownContract.Event.SelectGroup(groups[it]!!))
                                viewModel.setEvent(
                                    GroupDropdownContract.Event.ToggleDropdown(
                                        expanded
                                    )
                                )
                            }
                        ) {
                            Text(
                                text = groups[it]?.name ?: "",
                                modifier = Modifier.fillMaxWidth().padding(2.dp)
                            )
                            Text(
                                text = groups[it]?.speciality?.name ?: "",
                                modifier = Modifier.fillMaxWidth().padding(2.dp)
                            )
                        }
                    }

                    pagingLoadStateIndicator(groups)
                }
            },
            onClick = { }
        )

    }
}