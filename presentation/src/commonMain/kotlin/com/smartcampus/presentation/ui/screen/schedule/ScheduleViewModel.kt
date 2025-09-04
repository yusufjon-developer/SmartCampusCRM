package com.smartcampus.presentation.ui.screen.schedule

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.models.ScheduleCreateRequest
import com.smartcampus.crm.domain.models.ScheduleDto
import com.smartcampus.crm.domain.repositories.AuditoriumRepository
import com.smartcampus.crm.domain.repositories.GroupRepository
import com.smartcampus.crm.domain.repositories.ScheduleRepository
import com.smartcampus.crm.domain.repositories.TeacherRepository
import com.smartcampus.crm.domain.repositories.WorkloadRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import com.smartcampus.presentation.util.toServerFormat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ScheduleViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val auditoriumRepository: AuditoriumRepository,
    private val teacherRepository: TeacherRepository,
    private val groupRepository: GroupRepository,
    private val workloadRepository: WorkloadRepository
) : BaseViewModel<ScheduleContract.Event, ScheduleContract.Effect, ScheduleContract.State>() {

    init {
        loadWeeklySchedule()
    }

    override fun createInitialState() = ScheduleContract.State()

    override fun handleEvent(event: ScheduleContract.Event) {
        when (event) {
            is ScheduleContract.Event.SelectWeek -> {
                setState { copy(startDay = event.start, endDay = event.end) }
                loadWeeklySchedule()
            }

            is ScheduleContract.Event.SelectGroup -> {
                setState { copy(selectedGroup = event.group) }
                loadWeeklySchedule()
            }

            is ScheduleContract.Event.SelectAuditorium -> {
                setState { copy(selectedAuditorium = event.auditorium) }
                loadWeeklySchedule()
            }

            is ScheduleContract.Event.SelectTeacher -> {
                setState { copy(selectedTeacher = event.teacher) }
                loadWeeklySchedule()
            }

            is ScheduleContract.Event.LoadWeeklySchedules -> {
                loadWeeklySchedule()
            }

            is ScheduleContract.Event.LoadSchedule -> {
                loadSchedule(event.index, event.day, event.groupId)
            }

            is ScheduleContract.Event.Validate -> {

            }
            else -> {}
        }
    }

    private fun loadWeeklySchedule() {
        viewModelScope.launch(Dispatchers.IO) {
            setState { copy(isLoading = true) }

            val allSchedules = mutableListOf<List<ScheduleDto>>()
            val allErrors = mutableListOf<Map<String, NetworkError>>()
            var currentDate = uiState.value.startDay

            repeat(7) {
                val response = scheduleRepository.getSchedule(
                    day = currentDate.toServerFormat(),
                    teacherId = uiState.value.selectedTeacher?.id,
                    groupId = uiState.value.selectedGroup?.id,
                    auditoriumId = uiState.value.selectedAuditorium?.id,
                ).first()

                when (response) {
                    is Either.Left -> {
                        allErrors.add(mapOf(currentDate.toServerFormat() to response.value))
                        allSchedules.add(emptyList())
                    }

                    is Either.Right -> {
                        allSchedules.add(response.value)
                        allErrors.add(emptyMap())
                    }
                }

                currentDate = currentDate.plusDays(1)
            }

            setState {
                copy(
                    isLoading = false,
                    schedules = allSchedules,
                    error = allErrors
                )
            }
        }
    }

    private fun loadSchedule(index: Int, day: String, groupId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val allSchedules = uiState.value.schedules.toMutableList()
            val allErrors = uiState.value.error.toMutableList()

            val response = scheduleRepository.getSchedule(
                day = day,
                teacherId = uiState.value.selectedTeacher?.id,
                groupId = groupId,
                auditoriumId = uiState.value.selectedAuditorium?.id,
            ).first()

            when (response) {
                is Either.Left -> {
                    allErrors[index] = mapOf(day to response.value)
                    allSchedules[index] = emptyList()
                }

                is Either.Right -> {
                    allSchedules[index] = response.value
                    allErrors[index] = emptyMap()
                }
            }

        }
    }

    private fun validate(request: ScheduleCreateRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = scheduleRepository.validateRequest(request).first()
            when (response) {
                is Either.Left -> {
                    setEffect { ScheduleContract.Effect.ShowError(response.value) }
                }
                is Either.Right -> {
                    setEffect { ScheduleContract.Effect.ShowSnackbar("Schedule created successfully") }
                }
            }
        }
    }
}