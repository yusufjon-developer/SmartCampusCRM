package com.smartcampus.presentation.ui.widgets.teacherDropdown

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
import app.cash.paging.compose.itemKey
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeacherDropdown(
    initialSelection: TeacherDetailsDto?,
    onTeacherSelected: (TeacherDetailsDto) -> Unit,
    modifier: Modifier = Modifier,
    expanded: Boolean = false,
    onExpandedChange: (Boolean) -> Unit = {},
    viewModel: TeacherDropdownViewModel = koinViewModel(),
) {

    val state = viewModel.uiState.collectAsState()
    val teachers = state.value.teachers.collectAsLazyPagingItems()
    val dropDownIcon = if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown
    val selectedTeacher = state.value.selectedTeacher

    LaunchedEffect(initialSelection) {
        viewModel.setEvent(TeacherDropdownContract.Event.SelectTeacher(initialSelection))
    }

    Box(modifier = modifier) {
        OutlinedTextField(
            value = selectedTeacher?.let {
                "${it.surname} ${it.name?.firstOrNull() ?: ""}. ${it.lastname?.firstOrNull() ?: ""}."
            } ?: " ",
            onValueChange = { },
            label = { Text(text = "Преподаватель") },
            singleLine = true,
            suffix = {
                Icon(
                    imageVector = dropDownIcon,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        if (!expanded) viewModel.setEvent(TeacherDropdownContract.Event.LoadTeachers)
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
            modifier = Modifier.height(480.dp).width(600.dp),
        ) {
            DropdownMenuItem(
                text = {
                    LazyColumn(
                        modifier = Modifier.height(480.dp).width(600.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        items(
                            count = teachers.itemCount,
                            key = teachers.itemKey { it.id }
                        ) { index ->
                            val teacher = teachers[index]
                            if (teacher != null) {
                                Column(
                                    modifier = Modifier.clickable {
                                        viewModel.setEvent(TeacherDropdownContract.Event.SelectTeacher(teacher))
                                        onTeacherSelected(teacher)
                                        onExpandedChange(false)
                                    }
                                ) {
                                    val fullName = buildString {
                                        teacher.surname?.let { append("${teacher.id}. $it") }
                                        teacher.name?.let { append(" $it") }
                                        teacher.lastname?.let { append(" $it") }
                                    }
                                    Text(
                                        text = fullName.ifEmpty { "Фамилия: ${teacher.surname ?: ""}, Имя: ${teacher.name?.firstOrNull() ?: ""}., Отчество: ${teacher.lastname?.firstOrNull() ?: ""}." },
                                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
                                    )
                                }
                                HorizontalDivider()
                            }
                        }

                        pagingLoadStateIndicator(teachers)
                    }
                },
                onClick = { }
            )
        }
    }
}