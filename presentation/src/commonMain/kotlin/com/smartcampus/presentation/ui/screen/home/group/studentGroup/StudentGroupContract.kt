package com.smartcampus.presentation.ui.screen.home.group.studentGroup

import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.models.StudentDetailsDto
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.contract.UiEffect
import com.smartcampus.presentation.core.base.contract.UiEvent
import com.smartcampus.presentation.core.base.contract.UiState

interface StudentGroupContract {
    sealed interface Event : UiEvent {
        data class GetStudentsForGroup(val groupId: Int) : Event
        data class GetGroupById(val groupId: Int) : Event
        data class NavigateToProfileStudent(val studentId: Int) : Event
    }

    sealed interface Effect : UiEffect {
        data class ShowError(val error: NetworkError? = null) : Effect
        data class NavigateToStudentProfile(val studentId: Int) : Effect
    }

    data class State(
        val students: List<StudentDetailsDto> = emptyList(),
        val group: GroupDto? = null,
        val isLoading: Boolean = false
    ) : UiState
}