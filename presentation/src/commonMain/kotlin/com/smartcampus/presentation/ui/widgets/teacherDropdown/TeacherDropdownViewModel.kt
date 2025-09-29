package com.smartcampus.presentation.ui.widgets.teacherDropdown

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.repositories.TeacherRepository
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class TeacherDropdownViewModel(
    private val teacherRepository: TeacherRepository
) : BaseViewModel<TeacherDropdownContract.Event, TeacherDropdownContract.Effect, TeacherDropdownContract.State>() {

    override fun createInitialState() = TeacherDropdownContract.State()

    override fun handleEvent(event: TeacherDropdownContract.Event) {
        when (event) {
            is TeacherDropdownContract.Event.LoadTeachers -> {
                loadTeachers()
            }

            is TeacherDropdownContract.Event.SelectTeacher -> {
                setState { copy(selectedTeacher = event.teacher) }
            }
        }
    }

    private fun loadTeachers() {
        viewModelScope.launch(Dispatchers.IO) {
            setState {
                copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                // Предполагается, что TeacherRepository имеет метод getTeacherList(sortBy: String?)
                val teachersFlow = teacherRepository.getTeacherList(null)
                    .cachedIn(viewModelScope)
                    .catch { exception ->
                        val error = NetworkError.Unexpected(exception.message ?: "Unknown error")
                        setState {
                            copy(
                                isLoading = false,
                                error = error
                            )
                        }
                        setEffect { TeacherDropdownContract.Effect.ShowError(error) }
                    }

                setState {
                    copy(
                        isLoading = false,
                        teachers = teachersFlow,
                        error = null
                    )
                }
            } catch (e: Exception) {
                val error = NetworkError.Unexpected(e.message ?: "Unknown error")
                setState {
                    copy(
                        isLoading = false,
                        error = error
                    )
                }
                setEffect { TeacherDropdownContract.Effect.ShowError(error) }
            }
        }
    }
}