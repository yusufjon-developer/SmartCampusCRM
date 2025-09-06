package com.smartcampus.presentation.ui.screen.schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.AuditoriumDto
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.models.ScheduleDto
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.presentation.ui.widgets.groupDropdown.GroupDropdown
import com.smartcampus.presentation.util.toServerFormat
import org.koin.compose.viewmodel.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsState()

    ScheduleContent(
        state = state,
        onEvent = viewModel::setEvent
    )
}

@Composable
fun ScheduleContent(
    state: ScheduleContract.State,
    onEvent: (ScheduleContract.Event) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        ScheduleHeader(
            startDay = state.startDay,
            endDay = state.endDay,
            selectedGroup = state.selectedGroup,
            selectedTeacher = state.selectedTeacher,
            selectedAuditorium = state.selectedAuditorium,
            onWeekSelected = { start, end ->
                onEvent(
                    ScheduleContract.Event.SelectWeek(
                        start,
                        end
                    )
                )
            },
            onGroupSelected = { group -> onEvent(ScheduleContract.Event.SelectGroup(group)) },
            onTeacherSelected = { teacher -> onEvent(ScheduleContract.Event.SelectTeacher(teacher)) },
            onAuditoriumSelected = { auditorium ->
                onEvent(
                    ScheduleContract.Event.SelectAuditorium(
                        auditorium
                    )
                )
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        WeeklyScheduleList(
            schedules = state.schedules,
            startDay = state.startDay,
            isLoading = state.isLoading
        )
    }
}

@Composable
fun ScheduleHeader(
    startDay: LocalDate,
    endDay: LocalDate,
    selectedGroup: GroupDto?,
    selectedTeacher: TeacherDetailsDto?,
    selectedAuditorium: AuditoriumDto?,
    onWeekSelected: (LocalDate, LocalDate) -> Unit,
    onGroupSelected: (GroupDto) -> Unit,
    onTeacherSelected: (TeacherDetailsDto) -> Unit,
    onAuditoriumSelected: (AuditoriumDto) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Schedule: ${startDay.toServerFormat()} - ${endDay.toServerFormat()}",
                style = MaterialTheme.typography.headlineSmall
            )
            Button(onClick = {
                // Здесь должна быть логика выбора недели (календарь или кнопки переключения)
                // Пока заглушка для следующей недели
                onWeekSelected(startDay.plusDays(7), endDay.plusDays(7))
            }) {
                Text("Next Week")
            }
        }

        // Здесь можно добавить селекторы для группы, преподавателя, аудитории
        // Пока простые текстовые поля как пример
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GroupDropdown(
                selectGroup = selectedGroup,
                onGroupSelected = { onGroupSelected(it) }
            )

            OutlinedTextField(
                value = selectedTeacher?.let { "${it.id}" } ?: "",
                onValueChange = { /* Здесь логика выбора преподавателя */ },
                label = { Text("Teacher") },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = selectedAuditorium?.let { "${it.id}" } ?: "",
                onValueChange = { /* Здесь логика выбора аудитории */ },
                label = { Text("Auditorium") },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun WeeklyScheduleList(
    schedules: List<List<ScheduleDto>>,
    startDay: LocalDate,
    isLoading: Boolean,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                itemsIndexed(schedules) { index, daySchedules ->
                    val currentDay = startDay.plusDays(index.toLong())
                    DaySchedule(
                        date = currentDay,
                        schedules = daySchedules
                    )
                }
            }
        }
    }
}

@Composable
fun DaySchedule(
    date: LocalDate,
    schedules: List<ScheduleDto>,
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = date.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy")),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            if (schedules.isEmpty()) {
                Text(
                    text = "No schedule for this day",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    schedules.forEach { schedule ->
                        ScheduleItem(schedule = schedule)
                    }
                }
            }
        }
    }
}

@Composable
fun ScheduleItem(schedule: ScheduleDto) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "${schedule.startTime} - ${schedule.endTime}",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "Discipline ID: ${schedule.disciplineId ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Teacher ID: ${schedule.teacherId ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Auditorium ID: ${schedule.auditoriumId ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Type: ${schedule.type ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun ScheduleScreenPreview() {
    MaterialTheme {
        ScheduleContent(
            state = ScheduleContract.State(
                schedules = listOf(
                    listOf(
                        ScheduleDto(
                            id = 1,
                            day = "2025-09-01",
                            startTime = "09:00",
                            endTime = "10:30",
                            workloadId = null,
                            teacherId = 1,
                            groupId = 1,
                            disciplineId = 1,
                            auditoriumId = 1,
                            type = "LECTURE"
                        )
                    ),
                    emptyList() // Второй день без расписания
                ),
                startDay = LocalDate.now(),
                endDay = LocalDate.now().plusDays(6)
            ),
            onEvent = {}
        )
    }
}