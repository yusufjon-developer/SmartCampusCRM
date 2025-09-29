package com.smartcampus.presentation.ui.screen.employee.teachers

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

sealed interface TeachersContract {
    sealed interface Event : UiEvent {
        object GetTeachers : Event
        object AddTeacher : Event
        data class SearchTeacher(val query: String) : Event
        data class NavigateToTeacherProfile(val id: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class NavigateToTeacherProfile(val id: Int) : Effect
        object NavigateToAddTeacher : Effect
    }

    data class State(
        val teachers: Flow<PagingData<TeacherDetailsDto>> = emptyFlow(),
        val isLoading: Boolean = false,
        val searchQuery: String = ""
    ) : UiState
}
