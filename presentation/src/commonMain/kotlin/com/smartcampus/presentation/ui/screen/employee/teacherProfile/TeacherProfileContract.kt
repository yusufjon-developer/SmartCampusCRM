package com.smartcampus.presentation.ui.screen.employee.teacherProfile

import com.smartcampus.crm.domain.models.teacher.TeacherInfo
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

sealed interface TeacherProfileContract {
    sealed interface Event : UiEvent {
        data class GetTeacherInfo(val id: Int) : Event
        data class EditTeacherInfo(val id: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class NavigateToEdit(val id: Int) : Effect
    }

    data class State(
        val isLoading: Boolean = true,
        val teacherInfo: TeacherInfo? = null,
        val error: String? = null
    ) : UiState
}