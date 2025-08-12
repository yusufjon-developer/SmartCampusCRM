package com.smartcampus.presentation.ui.screen.employee.studentProfile

import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

class StudentProfileContract {
    sealed interface Event : UiEvent {
        data class GetStudentInfo(val id: Int) : Event
        data class EditStudentInfo(val id: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class NavigateToEdit(val id: Int) : Effect
    }

    data class State(
        val isLoading: Boolean = true,
        val studentInfo: StudentInfo? = null,
        val error: String? = null
    ) : UiState
}