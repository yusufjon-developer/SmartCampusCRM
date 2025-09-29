package com.smartcampus.presentation.ui.screen.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.domain.models.AuditoriumDto
import com.smartcampus.crm.domain.models.DisciplineDto
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.models.ScheduleCreateRequest
import com.smartcampus.crm.domain.models.ScheduleDto
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.crm.domain.models.WeeklyFreeSlotsRequest
import com.smartcampus.crm.domain.models.WeeklyScheduleResponse
import com.smartcampus.presentation.core.components.form.LoadingIndicatorDialog
import com.smartcampus.presentation.ui.widgets.auditoriumDropdown.AuditoriumDropdown
import com.smartcampus.presentation.ui.widgets.groupDropdown.GroupDropdown
import com.smartcampus.presentation.ui.widgets.teacherDropdown.TeacherDropdown
import com.smartcampus.presentation.util.toServerFormat
import org.koin.compose.viewmodel.koinViewModel
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Locale

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
    var selectedSchedule by remember { mutableStateOf<ScheduleDto?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var scheduleToDelete by remember { mutableStateOf<Int?>(null) }
    var validationResults by remember { mutableStateOf<Map<Int, Boolean>>(emptyMap()) }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
    ) {
        ScheduleHeader(
            startDay = state.startDay,
            endDay = state.endDay,
            selectedGroup = state.selectedGroup,
            selectedTeacher = state.selectedTeacher,
            selectedAuditorium = state.selectedAuditorium,
            onWeekSelected = { start, end ->
                onEvent(ScheduleContract.Event.SelectWeek(start, end))
            },
            onGroupSelected = { group -> onEvent(ScheduleContract.Event.SelectGroup(group)) },
            onTeacherSelected = { teacher -> onEvent(ScheduleContract.Event.SelectTeacher(teacher)) },
            onAuditoriumSelected = { auditorium ->
                onEvent(ScheduleContract.Event.SelectAuditorium(auditorium))
            },
            onClearFilters = { onEvent(ScheduleContract.Event.ClearFilters) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        WeeklyScheduleList(
            schedules = state.schedules,
            startDay = state.startDay,
            isLoading = state.isLoading,
            onScheduleEdit = { schedule ->
                selectedSchedule = schedule
            },
            editingScheduleId = selectedSchedule?.id,
            validationResults = validationResults
        )

        Spacer(modifier = Modifier.height(16.dp))

        ScheduleEditor(
            schedule = selectedSchedule,
            startDay = state.startDay,
            endDay = state.endDay,
            weeklySchedule = state.weeklySchedule,
            onSave = { request ->
                if (selectedSchedule == null) {
                    onEvent(ScheduleContract.Event.CreateSchedule(request))
                } else {
                    val updatedSchedule = ScheduleDto(
                        id = selectedSchedule!!.id,
                        workloadId = request.workloadId,
                        day = request.day,
                        startTime = request.startTime,
                        endTime = request.endTime,
                        teacher = request.teacherId?.let { TeacherDetailsDto(id = it) } ?: selectedSchedule!!.teacher,
                        group = request.groupId?.let { GroupDto(id = it) } ?: selectedSchedule!!.group,
                        discipline = request.disciplineId?.let { DisciplineDto(id = it) } ?: selectedSchedule!!.discipline,
                        auditorium = request.auditoriumId?.let { AuditoriumDto(id = it, null, null) } ?: selectedSchedule!!.auditorium,
                        type = request.type
                    )
                    onEvent(ScheduleContract.Event.UpdateSchedule(updatedSchedule))
                }
                selectedSchedule = null
            },
            onDelete = { id ->
                onEvent(ScheduleContract.Event.DeleteSchedule(id))
                selectedSchedule = null
            },
            onDismiss = {
                selectedSchedule = null
            },
            onLoadWeeklySchedule = { request ->
                onEvent(ScheduleContract.Event.ValidateWeek(request))
            },
            onSelectDay = { },
            onValidate = { request ->
                val daySchedule = state.weeklySchedule.days.firstOrNull { it.date == request.day }
                val slot = daySchedule?.slots?.firstOrNull { it.startTime == request.startTime && it.endTime == request.endTime }
                val isValid = slot?.isAvailable ?: false
                if (selectedSchedule != null) {
                    validationResults = validationResults + (selectedSchedule!!.id to isValid)
                }
                isValid
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
    onClearFilters: () -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var groupDropdownExpanded by remember { mutableStateOf(false) }
    var teacherDropdownExpanded by remember { mutableStateOf(false) }
    var auditoriumDropdownExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Расписание на неделю: ${startDay.toServerFormat()} - ${endDay.toServerFormat()}",
                style = MaterialTheme.typography.headlineSmall
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(onClick = onClearFilters) {
                    Text("Очистить фильтры")
                }
                Button(onClick = {
                    showDatePicker = true
                }) {
                    Text("Выбрать дату")
                }

                Button(onClick = {
                    val prevWeekStart = startDay.minusDays(7)
                    val prevWeekEnd = prevWeekStart.plusDays(6)
                    onWeekSelected(prevWeekStart, prevWeekEnd)
                }) {
                    Text("Пред. неделя")
                }

                Button(onClick = {
                    val nextWeekStart = startDay.plusDays(7)
                    val nextWeekEnd = nextWeekStart.plusDays(6)
                    onWeekSelected(nextWeekStart, nextWeekEnd)
                }) {
                    Text("След. неделя")
                }
            }
        }

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = startDay.atStartOfDay()
                    .atZone(ZoneId.systemDefault())
                    .toInstant()
                    .toEpochMilli()
            )

            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let { millis ->
                                val selectedDate = LocalDate.ofInstant(
                                    Instant.ofEpochMilli(millis),
                                    ZoneId.systemDefault()
                                )

                                val weekStart = selectedDate.with(
                                    TemporalAdjusters.previousOrSame(
                                        DayOfWeek.MONDAY
                                    )
                                )
                                val weekEnd = weekStart.plusDays(6)

                                onWeekSelected(weekStart, weekEnd)
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("Ок")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Отмена")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            GroupDropdown(
                initialSelection = selectedGroup,
                onGroupSelected = onGroupSelected,
                expanded = groupDropdownExpanded,
                onExpandedChange = { groupDropdownExpanded = it },
                modifier = Modifier.weight(1f).fillMaxWidth()
            )

            TeacherDropdown(
                initialSelection = selectedTeacher,
                onTeacherSelected = onTeacherSelected,
                expanded = teacherDropdownExpanded,
                onExpandedChange = { teacherDropdownExpanded = it },
                modifier = Modifier.weight(2f).fillMaxWidth()
            )

            AuditoriumDropdown(
                initialSelection = selectedAuditorium,
                onAuditoriumSelected = onAuditoriumSelected,
                expanded = auditoriumDropdownExpanded,
                onExpandedChange = { auditoriumDropdownExpanded = it },
                modifier = Modifier.weight(1f).fillMaxWidth(),
                isAvailable = false,
                day = startDay.toServerFormat()
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleEditor(
    schedule: ScheduleDto?,
    startDay: LocalDate,
    endDay: LocalDate,
    weeklySchedule: WeeklyScheduleResponse,
    onSave: (ScheduleCreateRequest) -> Unit,
    onDelete: ((Int) -> Unit)? = null,
    onDismiss: () -> Unit,
    onLoadWeeklySchedule: (WeeklyFreeSlotsRequest) -> Unit,
    onSelectDay: (String) -> Unit,
    onValidate: (ScheduleCreateRequest) -> Boolean = { true },
) {
    var selectedDay by remember { mutableStateOf(schedule?.day ?: startDay.toServerFormat()) }
    var selectedTeacher by remember { mutableStateOf(schedule?.teacher) }
    var selectedSlot by remember { mutableStateOf<Pair<LocalTime, LocalTime>?>(null) }
    var selectedGroup by remember { mutableStateOf(schedule?.group) }
    var selectedDiscipline by remember { mutableStateOf(schedule?.discipline) }
    var selectedAuditorium by remember { mutableStateOf(schedule?.auditorium) }
    var type by remember { mutableStateOf(schedule?.type) }
    var isValid by remember { mutableStateOf(true) }
    var isSlotSelectionPhase by remember { mutableStateOf(schedule == null) }
    var isLoadingSlots by remember { mutableStateOf(false) }
    var availableSlots by remember { mutableStateOf<List<Pair<LocalTime, LocalTime>>>(emptyList()) }
    var teacherDropdownExpanded by remember { mutableStateOf(false) }
    var errorEmptyDataMessage by remember { mutableStateOf<String?>(null) }
    var hasFreeSlots by remember { mutableStateOf(false) } // Новый флаг для наличия свободных слотов
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = startDay.plusDays(1).atStartOfDay()
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli(),
        initialDisplayMode = DisplayMode.Input,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                val date = Instant.ofEpochMilli(utcTimeMillis)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
                return date.isAfter(startDay.minusDays(1)) && date.isBefore(endDay.plusDays(1))
            }
        }
    )
    val datePickerFormatter = remember {
        DatePickerDefaults.dateFormatter(
            selectedDateDescriptionSkeleton = "EEEE (d.M)"
        )
    }

    LaunchedEffect(
        selectedDay,
        selectedSlot,
        selectedGroup,
        selectedDiscipline,
        selectedAuditorium,
        type
    ) {
        if (!isSlotSelectionPhase && selectedSlot != null) {
            errorEmptyDataMessage = null
            val request = ScheduleCreateRequest(
                workloadId = schedule?.workloadId,
                day = selectedDay,
                startTime = selectedSlot!!.first.toString(),
                endTime = selectedSlot!!.second.toString(),
                teacherId = selectedTeacher?.id,
                groupId = selectedGroup?.id,
                disciplineId = selectedDiscipline?.id,
                auditoriumId = selectedAuditorium?.id,
                type = type
            )
            isValid = onValidate(request)
        } else {
            val errorMessage = buildString {
                append("Выберите")
                if (selectedTeacher == null) append(" преподавателя,")
                if (selectedGroup == null) append(" группу,")
                if (selectedDiscipline == null) append(" дисциплину,")
                if (selectedAuditorium == null) append(" аудиторию,")
                if (type == null) append(" тип занятия,")
            }

            errorEmptyDataMessage =
                if (errorMessage.length != 8) errorMessage.dropLast(1) + "."
                else "$errorMessage."
        }
    }

    LaunchedEffect(schedule) {
        if (schedule != null) {
            isSlotSelectionPhase = false
            selectedDay = schedule.day
            selectedTeacher = schedule.teacher
            selectedSlot = Pair(
                LocalTime.parse(schedule.startTime),
                LocalTime.parse(schedule.endTime)
            )
            selectedGroup = schedule.group
            selectedDiscipline = schedule.discipline
            selectedAuditorium = schedule.auditorium
            type = schedule.type ?: ""
            // Load will be triggered by the effect below
        }
    }

    // Consolidated loading effect
    LaunchedEffect(selectedTeacher?.id, selectedDay) {
        if (selectedTeacher != null && selectedDay.isNotEmpty() && !isLoadingSlots) {
            isLoadingSlots = true
            onLoadWeeklySchedule(
                WeeklyFreeSlotsRequest(
                    selectedDay,
                    selectedDay,
                    selectedTeacher?.id
                )
            )
        }
    }

    LaunchedEffect(datePickerState.selectedDateMillis) {
        if (datePickerState.selectedDateMillis != null) {
            datePickerState.displayMode = DisplayMode.Input

            val localDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            selectedDay = localDate.toServerFormat()
            // Load will be triggered by the consolidated effect
        }
    }

    // Effect for checking free slots after loading weeklySchedule
    LaunchedEffect(weeklySchedule) {
        val freeSlots = weeklySchedule.days.flatMap { day ->
            day.slots.filter { it.isAvailable }
        }
        hasFreeSlots = freeSlots.isNotEmpty()
        availableSlots = freeSlots.map { Pair(LocalTime.parse(it.startTime), LocalTime.parse(it.endTime)) }
        isLoadingSlots = false // Complete loading after processing
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary.copy(alpha = .15f))
            .padding(8.dp)
            .heightIn(min = 200.dp, max = 600.dp)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(8.dp)
        ) {
            if (isLoadingSlots) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.clickable { isLoadingSlots = false }
                    )
                }
            } else {
                errorEmptyDataMessage?.let { EmptyDataError(it) }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TeacherDropdown(
                        initialSelection = selectedTeacher,
                        onTeacherSelected = {
                            selectedTeacher = it
                            // Load will be triggered by the consolidated effect
                        },
                        expanded = teacherDropdownExpanded,
                        onExpandedChange = { teacherDropdownExpanded = it },
                        modifier = Modifier.weight(1f)
                    )

                    DatePicker(
                        state = datePickerState,
                        dateFormatter = datePickerFormatter,
                        title = { Text("Выберите дату", modifier = Modifier.padding(8.dp)) },
                        headline = {
                            datePickerState.selectedDateMillis?.let {
                                Text(
                                    LocalDate.ofEpochDay(it / (24 * 60 * 60 * 1000L))
                                        .format(
                                            DateTimeFormatter.ofPattern(
                                                "EEEE, d MMMM",
                                                Locale("ru", "Ru")
                                            )
                                        ).replaceFirstChar { char -> char.uppercase() },
                                    modifier = Modifier.padding(8.dp)
                                )
                            }
                        },
                        modifier = Modifier.weight(1f).clip(RoundedCornerShape(16.dp))
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                val daySchedule = weeklySchedule.days.firstOrNull { it.date == selectedDay }
                if (daySchedule != null) {
                    Text(
                        "Доступные слоты на ${daySchedule.date}:",
                        fontWeight = FontWeight.Bold
                    )
                    LazyColumn(modifier = Modifier.height(200.dp)) {
                        items(daySchedule.slots.filter { it.isAvailable }) { slot ->
                            val slotStart = LocalTime.parse(slot.startTime)
                            val slotEnd = LocalTime.parse(slot.endTime)
                            val slotPair = Pair(slotStart, slotEnd)
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        selectedSlot = slotPair
                                    },
                                colors = if (selectedSlot == slotPair) {
                                    CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                                } else {
                                    CardDefaults.cardColors()
                                }
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("${slot.startTime} - ${slot.endTime}")
                                    if (slot.isAvailable) {
                                        Text("Свободен", color = Color.Green)
                                    } else {
                                        Text("Занят", color = Color.Red)
                                    }
                                }
                            }
                        }
                    }
                } else if (selectedTeacher != null) {
                    Text(
                        text = "Нет доступных слотов для выбранного дня",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Text(
                    text = "Выбранный слот: ${selectedDay} ${selectedSlot?.first ?: ""} - ${selectedSlot?.second ?: ""}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                var groupDropdownExpanded by remember { mutableStateOf(false) }
                var auditoriumDropdownExpanded by remember { mutableStateOf(false) }

                if (hasFreeSlots && selectedSlot != null) { // Активация полей только при наличии свободных слотов и выбранном слоте
                    GroupDropdown(
                        initialSelection = selectedGroup,
                        onGroupSelected = { selectedGroup = it },
                        expanded = groupDropdownExpanded,
                        onExpandedChange = { groupDropdownExpanded = it },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (selectedDiscipline != null) {
                        OutlinedTextField(
                            value = selectedDiscipline?.subject?.name ?: "N/A",
                            onValueChange = { /* not editable */ },
                            label = { Text("Дисциплина") },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = false
                        )
                    }

                    AuditoriumDropdown(
                        initialSelection = selectedAuditorium,
                        onAuditoriumSelected = { selectedAuditorium = it },
                        expanded = auditoriumDropdownExpanded,
                        onExpandedChange = { auditoriumDropdownExpanded = it },
                        modifier = Modifier.fillMaxWidth(),
                        isAvailable = true,
                        day = selectedDay
                    )

                    OutlinedTextField(
                        value = type ?: "",
                        onValueChange = { type = it },
                        label = { Text("Тип занятия (Лекция, Практика, Лабораторная...)") },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = "Выберите свободный слот для активации полей",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                if (!isValid) {
                    Text(
                        text = "Проверьте правильность заполнения данных",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (schedule == null) {
                    // Добавление
                    Button(
                        onClick = {
                            if (selectedDay.isNotEmpty() && selectedSlot != null && hasFreeSlots) {
                                val request = ScheduleCreateRequest(
                                    workloadId = null,
                                    day = selectedDay,
                                    startTime = selectedSlot!!.first.toString(),
                                    endTime = selectedSlot!!.second.toString(),
                                    teacherId = selectedTeacher?.id,
                                    groupId = selectedGroup?.id,
                                    disciplineId = selectedDiscipline?.id,
                                    auditoriumId = selectedAuditorium?.id,
                                    type = type?.ifEmpty { null }
                                )
                                onSave(request)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = hasFreeSlots && selectedSlot != null
                    ) {
                        Text("Добавить")
                    }

                    OutlinedButton(
                        onClick = {
                            selectedDay = ""
                            selectedTeacher = null
                            selectedSlot = null
                            selectedGroup = null
                            selectedDiscipline = null
                            selectedAuditorium = null
                            type = ""
                            hasFreeSlots = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Очистить")
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Отмена")
                    }
                } else {
                    // Редактирование
                    Button(
                        onClick = {
                            if (selectedDay.isNotEmpty() && selectedSlot != null && hasFreeSlots) {
                                val request = ScheduleCreateRequest(
                                    workloadId = schedule.workloadId,
                                    day = selectedDay,
                                    startTime = selectedSlot!!.first.toString(),
                                    endTime = selectedSlot!!.second.toString(),
                                    teacherId = selectedTeacher?.id,
                                    groupId = selectedGroup?.id,
                                    disciplineId = selectedDiscipline?.id,
                                    auditoriumId = selectedAuditorium?.id,
                                    type = type?.ifEmpty { null }
                                )
                                onSave(request)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        enabled = hasFreeSlots && selectedSlot != null
                    ) {
                        Text("Изменить")
                    }

                    OutlinedButton(
                        onClick = { onDelete?.invoke(schedule.id) },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
                    ) {
                        Text("Удалить")
                    }

                    OutlinedButton(
                        onClick = {
                            selectedDay = schedule.day
                            selectedTeacher = schedule.teacher
                            selectedSlot = Pair(
                                LocalTime.parse(schedule.startTime),
                                LocalTime.parse(schedule.endTime)
                            )
                            selectedGroup = schedule.group
                            selectedDiscipline = schedule.discipline
                            selectedAuditorium = schedule.auditorium
                            type = schedule.type ?: ""
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Очистить")
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Отмена")
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyDataError(
    error: String,
) {
    Text(
        text = error,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun WeeklyScheduleList(
    schedules: List<List<ScheduleDto>>,
    startDay: LocalDate,
    isLoading: Boolean,
    onScheduleEdit: (ScheduleDto) -> Unit = {},
    editingScheduleId: Int? = null,
    validationResults: Map<Int, Boolean> = emptyMap(),
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            LoadingIndicatorDialog(
                isLoading = isLoading
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(schedules) { index, daySchedules ->
                    val currentDay = startDay.plusDays(index.toLong())
                    DaySchedule(
                        date = currentDay,
                        schedules = daySchedules,
                        onScheduleEdit = onScheduleEdit,
                        editingScheduleId = editingScheduleId,
                        validationResults = validationResults
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
    onScheduleEdit: (ScheduleDto) -> Unit = {},
    editingScheduleId: Int? = null,
    validationResults: Map<Int, Boolean> = emptyMap(),
) {
    // Создаем стандартные временные слоты пар
    val scheduleSlots = listOf(
        LocalTime.of(8, 0) to LocalTime.of(9, 30),     // 1 пара
        LocalTime.of(9, 45) to LocalTime.of(11, 15),   // 2 пара
        LocalTime.of(11, 30) to LocalTime.of(13, 0),   // 3 пара
        LocalTime.of(14, 0) to LocalTime.of(15, 30),   // 4 пара (после 1-часового перерыва)
        LocalTime.of(15, 45) to LocalTime.of(17, 15)   // 5 пара
    )

    // Создаем карту для быстрого поиска расписания по времени
    val scheduleMap = schedules.associateBy { schedule ->
        LocalTime.parse(schedule.startTime) to LocalTime.parse(schedule.endTime)
    }

    Card(
        modifier = Modifier
            .width(300.dp) // Фиксированная ширина для каждого дня
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .padding(vertical = 16.dp, horizontal = 8.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = date.format(
                    DateTimeFormatter.ofPattern(
                        "EEEE\ndd MMMM",
                        Locale("ru", "Ru")
                    )
                ).capitalize(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                // Отображаем все 5 стандартных пар
                scheduleSlots.forEach { (startTime, endTime) ->
                    val schedule = scheduleMap[startTime to endTime]

                    if (schedule != null) {
                        // Есть занятие в этом слоте
                        val isEditing = editingScheduleId == schedule.id
                        val isValid = validationResults[schedule.id] ?: true
                        ScheduleItem(
                            schedule = schedule,
                            onScheduleEdit = onScheduleEdit,
                            isEditing = isEditing,
                            isValid = isValid
                        )
                    } else {
                        // Нет занятия - показываем пустой слот
                        EmptyScheduleSlot(
                            startTime = startTime,
                            endTime = endTime
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyScheduleSlot(
    startTime: LocalTime,
    endTime: LocalTime,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(124.dp)
            .clip(RoundedCornerShape(12.dp)),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${startTime.toString().substring(0, 5)} - ${
                    endTime.toString().substring(0, 5)
                }",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "Нет занятий",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ScheduleItem(
    schedule: ScheduleDto,
    onScheduleEdit: (ScheduleDto) -> Unit,
    isEditing: Boolean = false,
    isValid: Boolean = true,
) {
    val backgroundColor = when {
        isEditing && isValid -> MaterialTheme.colorScheme.primaryContainer
        isEditing && !isValid -> MaterialTheme.colorScheme.errorContainer
        else -> MaterialTheme.colorScheme.background
    }

    val textColor = when {
        isEditing && isValid -> MaterialTheme.colorScheme.onPrimaryContainer
        isEditing && !isValid -> MaterialTheme.colorScheme.onErrorContainer
        else -> MaterialTheme.colorScheme.onBackground
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { onScheduleEdit(schedule) },
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = "${schedule.startTime} - ${schedule.endTime}",
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )

            Text(
                text = "Группа: ${schedule.group?.name ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )

            Text(
                text = schedule.discipline?.subject?.name ?: "N/A",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = textColor,
                overflow = TextOverflow.Ellipsis
            )

            var fullName = buildString {
                schedule.teacher?.surname?.let { append(it) }
                schedule.teacher?.name?.let {
                    append(" ")
                    append(it.firstOrNull()?.uppercase())
                    append(".")
                }
                schedule.teacher?.lastname?.let {
                    append(" ")
                    append(it.firstOrNull()?.uppercase())
                    append(".")
                }
            }
            if (fullName.isEmpty()) fullName = "N/A"
            Text(
                text = fullName,
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )

            Text(
                text = (schedule.auditorium?.number ?: "N/A") +
                        if (schedule.auditorium?.type != null) ": ${schedule.auditorium?.type}" else "",
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )

            Text(
                text = "Тип: ${schedule.type ?: "N/A"}",
                style = MaterialTheme.typography.bodyMedium,
                color = textColor
            )
        }
    }
}