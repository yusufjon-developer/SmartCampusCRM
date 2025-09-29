package com.smartcampus.presentation.ui.screen.schedule

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.models.ScheduleCreateRequest
import com.smartcampus.crm.domain.models.ScheduleDto
import com.smartcampus.crm.domain.models.ScheduleUpdateRequest
import com.smartcampus.crm.domain.models.WeeklyFreeSlotsRequest
import com.smartcampus.crm.domain.repositories.ScheduleRepository
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

            is ScheduleContract.Event.CreateSchedule -> {
                createSchedule(event.schedule)
            }

            is ScheduleContract.Event.UpdateSchedule -> {
                updateSchedule(event.schedule)
            }

            is ScheduleContract.Event.DeleteSchedule -> {
                deleteSchedule(event.scheduleId)
            }

            is ScheduleContract.Event.ValidateWeek -> {
                validateWeek(event.request)
            }

            ScheduleContract.Event.ClearFilters -> {
                setState { copy(
                    selectedTeacher = null,
                    selectedGroup = null,
                    selectedAuditorium = null
                ) }
                loadWeeklySchedule()
            }
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

            setState {
                copy(
                    schedules = allSchedules,
                    error = allErrors
                )
            }
        }
    }

    private fun createSchedule(request: ScheduleCreateRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = scheduleRepository.createSchedule(request).first()
            when (response) {
                is Either.Left -> {
                    setEffect { ScheduleContract.Effect.ShowError(response.value) }
                }
                is Either.Right -> {
                    setEffect { ScheduleContract.Effect.ShowSnackbar("Расписание успешно создано") }
                    loadWeeklySchedule() // Обновить расписание
                }
            }
        }
    }

    private fun updateSchedule(schedule: ScheduleDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val request = ScheduleUpdateRequest(
                workloadId = schedule.workloadId,
                day = schedule.day,
                startTime = schedule.startTime,
                endTime = schedule.endTime,
                teacherId = schedule.teacher?.id,
                groupId = schedule.group?.id,
                disciplineId = schedule.discipline?.id,
                auditoriumId = schedule.auditorium?.id,
                type = schedule.type
            )

            val response = scheduleRepository.updateSchedule(schedule.id, request).first()
            when (response) {
                is Either.Left -> {
                    setEffect { ScheduleContract.Effect.ShowError(response.value) }
                }
                is Either.Right -> {
                    setEffect { ScheduleContract.Effect.ShowSnackbar("Расписание успешно обновлено") }
                    loadWeeklySchedule() // Обновить расписание
                }
            }
        }
    }

    private fun deleteSchedule(scheduleId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = scheduleRepository.deleteSchedule(scheduleId).first()
            when (response) {
                is Either.Left -> {
                    setEffect { ScheduleContract.Effect.ShowError(response.value) }
                }
                is Either.Right -> {
                    setEffect { ScheduleContract.Effect.ShowSnackbar("Расписание успешно удалено") }
                    loadWeeklySchedule() // Обновить расписание
                }
            }
        }
    }

    private fun validateWeek(request: WeeklyFreeSlotsRequest) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = scheduleRepository.validateWeek(request).first()
            when (response) {
                is Either.Left -> {
                    setEffect { ScheduleContract.Effect.ShowError(response.value) }
                }
                is Either.Right -> {
                    setState { copy(weeklySchedule = response.value) }
                }
            }
        }
    }
}