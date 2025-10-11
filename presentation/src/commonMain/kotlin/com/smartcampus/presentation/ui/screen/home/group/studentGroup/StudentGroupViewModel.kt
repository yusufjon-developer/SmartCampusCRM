package com.smartcampus.presentation.ui.screen.home.group.studentGroup

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.repositories.GroupRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class StudentGroupViewModel(
    private val groupRepository: GroupRepository
) : BaseViewModel<StudentGroupContract.Event, StudentGroupContract.Effect, StudentGroupContract.State>() {

    override fun createInitialState() = StudentGroupContract.State()

    override fun handleEvent(event: StudentGroupContract.Event) {
        when (event) {
            is StudentGroupContract.Event.GetStudentsForGroup -> {
                loadGroupWithStudents(event.groupId)
            }

            is StudentGroupContract.Event.GetGroupById -> {
                loadGroupById(event.groupId)
            }

            is StudentGroupContract.Event.NavigateToProfileStudent -> {
                setEffect { StudentGroupContract.Effect.NavigateToStudentProfile(event.studentId) }
            }
        }
    }

    private fun loadGroupWithStudents(groupId: Int) {
        viewModelScope.launch {
            groupRepository.getStudentsByGroup(groupId)
                .onStart {
                    setState { copy(isLoading = true) }
                }
                .onEach { students ->
                    when (students) {
                        is Either.Left -> {
                            setEffect { StudentGroupContract.Effect.ShowError(students.value) }
                        }

                        is Either.Right -> {
                            setState { copy(students = students.value) }
                        }
                    }
                }
                .onCompletion {
                    setState { copy(isLoading = false) }
                }
                .launchIn(viewModelScope)
        }
    }

    private fun loadGroupById(groupId: Int) {
        viewModelScope.launch {
            groupRepository.getGroupById(groupId)
                .onStart {
                    setState { copy(isLoading = true) }
                }
                .onEach { group ->
                    when (group) {
                        is Either.Left ->
                            setEffect { StudentGroupContract.Effect.ShowError(group.value) }
                        is Either.Right ->
                            setState { copy(group = group.value) }
                    }
                }
                .onCompletion {
                    setState { copy(isLoading = false) }
                }
                .launchIn(viewModelScope)
        }
    }
}