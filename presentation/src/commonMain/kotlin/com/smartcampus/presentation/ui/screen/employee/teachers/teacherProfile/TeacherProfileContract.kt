package com.smartcampus.presentation.ui.screen.employee.teachers.teacherProfile

import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.crm.domain.models.TeacherSensitiveDto
import com.smartcampus.crm.domain.models.TeacherUpdateRequest
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

class TeacherProfileContract {
    sealed interface Event : UiEvent {
        data class GetTeacher(val id: Int) : Event
        data class GetTeacherInfo(val id: Int) : Event
        data class AddTeacher(val newTeacher: RegisterRequest) : Event
        data class UpdateTeacher(val request: TeacherUpdateRequest) : Event
        data class DeleteTeacher(val id: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowErrorTeacher(val error: NetworkError) : Effect
        data class ShowErrorTeacherInfo(val error: NetworkError) : Effect
        data class ShowSuccessMessage(val message: String) : Effect
        data class AddTeacher(val id: Int) : Effect
        object TeacherDeleted : Effect
    }

    data class State(
        val isLoading: Boolean = false,
        val teacher: TeacherDetailsDto? = null,
        val teacherInfo: TeacherSensitiveDto? = null
    ) : UiState
}
