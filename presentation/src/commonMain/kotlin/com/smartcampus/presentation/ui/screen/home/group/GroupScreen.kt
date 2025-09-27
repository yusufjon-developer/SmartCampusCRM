package com.smartcampus.presentation.ui.screen.home.group

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.collectAsLazyPagingItems
import app.cash.paging.compose.itemKey
import com.smartcampus.crm.domain.models.GroupCreateRequest
import com.smartcampus.crm.domain.models.SpecialityDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.button.AddButton
import com.smartcampus.presentation.core.components.form.ErrorDialog
import com.smartcampus.presentation.core.components.text.InfoItem
import com.smartcampus.presentation.core.extensions.pagingLoadStateIndicator
import com.smartcampus.presentation.ui.widgets.specialityDropdown.SpecialityDropdown
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GroupScreen(
    viewModel: GroupViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val groups = uiState.groups.collectAsLazyPagingItems()

    val snackbarHostState = remember { SnackbarHostState() }

    var newGroupName by remember { mutableStateOf("") }
    var newGroupCourse by remember { mutableStateOf("") }

    var selectedSpeciality by remember { mutableStateOf<SpecialityDto?>(null) }
    var specialtyError by remember { mutableStateOf<String?>(null) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var courseError by remember { mutableStateOf<String?>(null) }

    var showAddDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf<NetworkError?>(null) }

    val expandedState = remember { mutableStateMapOf<Int, Boolean>() }

    LaunchedEffect(Unit) {
        viewModel.setEvent(GroupContract.Event.GetGroups)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is GroupContract.Effect.ShowError -> {
                    snackbarHostState.showSnackbar(effect.error.toString())
                }

                is GroupContract.Effect.ShowSuccessMessage -> {
                    snackbarHostState.showSnackbar(effect.message)
                }

                is GroupContract.Effect.ShowAddDialog -> {
                    showAddDialog = effect.show
                    groups.refresh()
                    viewModel.setEvent(GroupContract.Event.GetGroups)
                }
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            AddButton(
                onClick = { viewModel.setEvent(GroupContract.Event.ShowAddDialog(show = true)) },
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                shape = RoundedCornerShape(12.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    count = groups.itemCount,
                    key = groups.itemKey { it.id }
                ) { index ->
                    val group = groups[index]
                    if (group != null) {
                        val isExpanded = expandedState[group.id] ?: false

                        InfoItem(
                            infoName = group.name.toString(),
                            infoSecondaryText = "Курс:  ${group.course?.toString().orEmpty()}",
                            infoDescription = "Специальность:  ${group.speciality?.name.toString()}",
                            expanded = isExpanded,
                            onExpandedChange = { newExpandedState ->
                                if (newExpandedState) {
                                    expandedState.keys.forEach { id ->
                                        if (id != group.id) expandedState[id] = false
                                    }
                                }
                                expandedState[group.id] = newExpandedState
                            }
                        )
                    }
                }

                pagingLoadStateIndicator(
                    lazyPagingItems = groups,
                    emptyListMessage = "Группы не найдены"
                )
            }

            showErrorDialog?.let { error ->
                ErrorDialog(
                    title = "Ошибка",
                    message = error.toString(),
                    onDismiss = { showErrorDialog = null },
                    onCopy = { }
                )
            }
            println("showAddDialog: $showAddDialog")

            if (showAddDialog) {
                AlertDialog(
                    onDismissRequest = {
                        showAddDialog = false
                        viewModel.setEvent(GroupContract.Event.ShowAddDialog(false))
                        newGroupName = ""
                        newGroupCourse = ""
                        selectedSpeciality = null
                        nameError = null
                        courseError = null
                        specialtyError = null
                    },
                    title = { Text("Добавить новую группу") },
                    text = {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = newGroupName,
                                onValueChange = {
                                    newGroupName = it
                                    if (nameError != null) nameError = null
                                },
                                label = { Text("Название группы") },
                                singleLine = true,
                                isError = nameError != null,
                                supportingText = {
                                    if (nameError != null) {
                                        Text(
                                            text = nameError!!,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                }
                            )

                            SpecialityDropdown(
                                selectSpeciality = null,
                                onSpecialitySelected = { speciality ->
                                    selectedSpeciality = speciality
                                    if (specialtyError != null) specialtyError = null
                                },
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (specialtyError != null) {
                                Text(
                                    text = specialtyError!!,
                                    color = MaterialTheme.colorScheme.error,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                                )
                            }

                            OutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = newGroupCourse,
                                onValueChange = {
                                    newGroupCourse = it.filter { char -> char.isDigit() }
                                    if (courseError != null) courseError = null
                                },
                                label = { Text("Курс") },
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                isError = courseError != null,
                                supportingText = {
                                    if (courseError != null) {
                                        Text(
                                            text = courseError!!,
                                            color = MaterialTheme.colorScheme.error
                                        )
                                    }
                                },
                            )
                        }
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val nameValid = newGroupName.isNotBlank()
                                val courseInt = newGroupCourse.trim().toIntOrNull()
                                val specialtyValid = selectedSpeciality != null

                                nameError =
                                    if (!nameValid) "Название группы не может быть пустым" else null
                                courseError =
                                    if (courseInt == null || newGroupCourse.isBlank()) "Курс должен быть числом" else null
                                specialtyError =
                                    if (!specialtyValid) "Необходимо выбрать специальность" else null

                                if (nameValid && courseInt != null && specialtyValid) {
                                    val groupRequest = GroupCreateRequest(
                                        name = newGroupName.trim(),
                                        specId = selectedSpeciality!!.id,
                                        course = courseInt
                                    )
                                    viewModel.setEvent(GroupContract.Event.AddGroup(groupRequest))
                                }
                            }
                        ) {
                            Text("Добавить")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                showAddDialog = false
                                viewModel.setEvent(GroupContract.Event.ShowAddDialog(false))
                                newGroupName = ""
                                newGroupCourse = ""
                                selectedSpeciality = null
                                nameError = null
                                courseError = null
                                specialtyError = null
                            }
                        ) {
                            Text("Отмена")
                        }
                    }
                )
            }
        }
    }
}