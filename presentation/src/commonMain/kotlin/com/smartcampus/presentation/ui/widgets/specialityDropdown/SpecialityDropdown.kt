package com.smartcampus.presentation.ui.widgets.specialityDropdown

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
import com.smartcampus.crm.domain.models.SpecialityDto
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SpecialityDropdown(
    selectSpeciality: SpecialityDto?,
    onSpecialitySelected: (SpecialityDto) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SpecialityDropdownViewModel = koinViewModel(),
) {
    val state = viewModel.uiState.collectAsState()
    val specialties = state.value.specialties.collectAsLazyPagingItems()
    val expanded = state.value.expanded
    val dropDownIcon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val selectedSpeciality = state.value.selectedSpeciality

    LaunchedEffect(Unit) {
        viewModel.setEvent(SpecialityDropdownContract.Event.LoadSpecialties)
        selectSpeciality?.let { viewModel.setEvent(SpecialityDropdownContract.Event.SelectSpeciality(it)) }
    }

    OutlinedTextField(
        value = selectedSpeciality?.name ?: " ",
        onValueChange = { },
        label = { Text(text = "Специальность") },
        singleLine = true,
        suffix = {
            Icon(
                imageVector = dropDownIcon,
                contentDescription = null,
                modifier = Modifier.clickable {
                    viewModel.setEvent(SpecialityDropdownContract.Event.ToggleDropdown(expanded))
                }
            )
        },
        modifier = modifier
    )
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = {
            viewModel.setEvent(SpecialityDropdownContract.Event.ToggleDropdown(expanded))
        },
        modifier = modifier.height(300.dp).width(300.dp),
    ) {
        DropdownMenuItem(
            text = {
                LazyColumn(
                    modifier = modifier.height(300.dp).width(300.dp),
                    contentPadding = PaddingValues(4.dp)
                ) {
                    items(specialties.itemCount) {
                        val speciality = specialties[it]
                        if (speciality != null) {
                            Column(
                                modifier = Modifier.fillMaxWidth().clickable {
                                    onSpecialitySelected(speciality)
                                    viewModel.setEvent(SpecialityDropdownContract.Event.SelectSpeciality(speciality))
                                    viewModel.setEvent(
                                        SpecialityDropdownContract.Event.ToggleDropdown(
                                            expanded
                                        )
                                    )
                                }
                            ) {
                                Text(
                                    text = speciality.name ?: "Нет названия",
                                    modifier = Modifier.fillMaxWidth().padding(2.dp)
                                )
                            }
                        }
                    }

                    pagingLoadStateIndicator(specialties)
                }
            },
            onClick = { }
        )

    }
}