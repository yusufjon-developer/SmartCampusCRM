package com.smartcampus.presentation.ui.screen.student

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.StudentDetailsDto
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

sealed interface StudentContract {
    sealed interface Event : UiEvent {
        object GetStudents : Event
        object AddStudent : Event
        data class SearchStudent(val query: String) : Event
        data class NavigateToStudentProfile(val id: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class NavigateToStudentProfile(val id: Int) : Effect
        object NavigateToAddStudent : Effect
    }

    data class State(
        val students: Flow<PagingData<StudentDetailsDto>> = emptyFlow(),
        val isLoading: Boolean = false,
        val searchQuery: String = ""
    ) : UiState
}