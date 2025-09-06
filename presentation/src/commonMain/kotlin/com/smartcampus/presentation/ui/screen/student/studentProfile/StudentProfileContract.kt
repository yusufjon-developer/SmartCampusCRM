package com.smartcampus.presentation.ui.screen.student.studentProfile

import com.smartcampus.crm.domain.models.StudentDetailsDto
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.StudentSensitiveDto
import com.smartcampus.crm.domain.models.StudentUpdateRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

class StudentProfileContract {
    sealed interface Event : UiEvent {
        data class AddStudent(val newStudent: RegisterRequest) : Event
        data class GetStudent(val id: Int) : Event
        data class GetStudentInfo(val id: Int) : Event
        data class UpdateStudent(val request: StudentUpdateRequest) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowErrorStudent(val error: NetworkError) : Effect
        data class ShowErrorStudentInfo(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
        data class AddStudent(val id: Int) : Effect
    }

    data class State(
        val isLoading: Boolean = false,
        val student: StudentDetailsDto? = null,
        val studentInfo: StudentSensitiveDto? = null
    ) : UiState
}