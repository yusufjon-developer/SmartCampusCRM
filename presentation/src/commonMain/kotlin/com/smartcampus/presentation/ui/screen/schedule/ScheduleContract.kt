package com.smartcampus.presentation.ui.screen.schedule

import com.smartcampus.crm.domain.models.AuditoriumDto
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.models.ScheduleCreateRequest
import com.smartcampus.crm.domain.models.ScheduleDto
import com.smartcampus.crm.domain.models.TeacherDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import com.smartcampus.presentation.util.getEndDay
import com.smartcampus.presentation.util.getStartDay
import java.time.LocalDate
import java.time.LocalDateTime

interface ScheduleContract {

    sealed interface Event : UiEvent {
        data class SelectWeek(
            val start: LocalDate,
            val end: LocalDate
        ) : Event
        data class SelectGroup(val group: GroupDto) : Event
        data class SelectAuditorium(val auditorium: AuditoriumDto) : Event
        data class SelectTeacher(val teacher: TeacherDto) : Event
        data class Validate(val schedule: ScheduleCreateRequest) : Event
        data class CreateSchedule(val schedule: ScheduleCreateRequest) : Event
        data class DeleteSchedule(val scheduleId: Int) : Event
        data class UpdateSchedule(val schedule: ScheduleDto) : Event
        object LoadWeeklySchedules : Event
        data class LoadSchedule(val index: Int, val day: String, val groupId: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowSnackbar(val message: String) : Effect
        data class ShowError(val error: NetworkError) : Effect
    }

    data class State(
        val isLoading: Boolean = false,
        val schedules: List<List<ScheduleDto>> = emptyList(),
        val startDay: LocalDate = LocalDateTime.now().getStartDay(),
        val endDay: LocalDate = LocalDateTime.now().getEndDay(),
        val selectedGroup: GroupDto? = null,
        val selectedAuditorium: AuditoriumDto? = null,
        val selectedTeacher: TeacherDto? = null,
        val weekDays: List<Map<String, String>> = emptyList(),
        val error: List<Map<String, NetworkError>> = emptyList()
    ) : UiState
}