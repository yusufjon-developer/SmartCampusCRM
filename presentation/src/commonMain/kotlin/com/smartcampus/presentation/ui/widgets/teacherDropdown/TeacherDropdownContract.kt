package com.smartcampus.presentation.ui.widgets.teacherDropdown

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

interface TeacherDropdownContract {

    sealed interface Event : UiEvent {
        object LoadTeachers : Event
        data class SelectTeacher(val teacher: TeacherDetailsDto?) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError) : Effect
    }

    data class State(
        val teachers: Flow<PagingData<TeacherDetailsDto>> = emptyFlow(),
        val isLoading: Boolean = false,
        val selectedTeacher: TeacherDetailsDto? = null,
        val error: NetworkError? = null
    ) : UiState
}