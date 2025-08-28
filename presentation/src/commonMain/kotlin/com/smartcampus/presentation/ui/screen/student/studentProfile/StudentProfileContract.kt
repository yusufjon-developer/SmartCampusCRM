package com.smartcampus.presentation.ui.screen.student.studentProfile

import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

class StudentProfileContract {
    sealed interface Event : UiEvent {
        object AddStudent : Event
        data class GetStudent(val id: Int) : Event
        data class GetStudentInfo(val id: Int) : Event
        data class UpdateStudent(val student: Student) : Event
        data class UpdateStudentInfo(val studentInfo: StudentInfo) : Event
    }

    sealed interface Effect : UiEffect {
        object AddStudent : Effect
    }

    data class State(
        val isLoading: Boolean = true,
        val student: Student? = null,
        val studentInfo: StudentInfo? = null,
        val error: String? = null
    ) : UiState
}