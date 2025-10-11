package com.smartcampus.presentation.ui.screen.home.group.studentGroup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.smartcampus.crm.domain.models.StudentDetailsDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.components.form.ErrorDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StudentGroupScreen(
    groupId: Int,
    navigateToStudentProfile: (Int) -> Unit,
    viewModel: StudentGroupViewModel = koinViewModel()
) {
    LaunchedEffect(groupId) {
        viewModel.setEvent(StudentGroupContract.Event.GetGroupById(groupId))
        viewModel.setEvent(StudentGroupContract.Event.GetStudentsForGroup(groupId))
    }

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val students = uiState.students
    var error by remember { mutableStateOf<NetworkError?>(null) }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is StudentGroupContract.Effect.ShowError -> {
                    error = effect.error
                }

                is StudentGroupContract.Effect.NavigateToStudentProfile -> {
                    navigateToStudentProfile(effect.studentId)
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)) {
            Column(modifier = Modifier.padding(12.dp)) {
                Text(
                    text = "Группа: ${uiState.group?.name}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(count = students.size) {studentIndex ->
                val student = students[studentIndex]
                StudentRow(studentIndex + 1, student) {
                    viewModel.setEvent(StudentGroupContract.Event.NavigateToProfileStudent(student.id))
                }
            }
        }

        if (error != null) {
            ErrorDialog(
                title = "Ошибка",
                message = error!!,
                onDismiss = { error = null },
                onCopy = { }
            )
        }
    }
}

@Composable
private fun StudentRow(number: Int, student: StudentDetailsDto, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Text(
                text = "$number.",
                modifier = Modifier.padding(end = 12.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Column {
                val fio =
                    listOfNotNull(student.surname, student.name, student.lastname).joinToString(" ")
                Text(text = fio, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = "Рожд.: ${student.birthday.orEmpty()}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Тел.: ${student.phoneNumber.orEmpty()}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
