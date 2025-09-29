package com.smartcampus.presentation.ui.screen.employee.teachers

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.repositories.TeacherRepository
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.launch

class TeachersViewModel(
    private val repository: TeacherRepository
) : BaseViewModel<TeachersContract.Event, TeachersContract.Effect, TeachersContract.State>() {

    override fun createInitialState() = TeachersContract.State()

    init {
        setEvent(TeachersContract.Event.GetTeachers)
    }

    override fun handleEvent(event: TeachersContract.Event) {
        when (event) {
            is TeachersContract.Event.GetTeachers -> getTeachers()
            is TeachersContract.Event.AddTeacher -> setEffect {TeachersContract.Effect.NavigateToAddTeacher }
            is TeachersContract.Event.SearchTeacher -> setState { copy(searchQuery = event.query) }
            is TeachersContract.Event.NavigateToTeacherProfile -> setEffect {
                TeachersContract.Effect.NavigateToTeacherProfile(event.id)
            }
        }
    }

    private fun getTeachers(sortBy: String? = null) {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val flow = repository.getTeacherList(sortBy).cachedIn(viewModelScope)
            setState { copy(isLoading = false, teachers = flow) }
        }
    }
}