package com.smartcampus.presentation.ui.screen.student

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.smartcampus.crm.domain.repositories.StudentRepository
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class StudentViewModel(
    private val repository: StudentRepository
) : BaseViewModel<StudentContract.Event, StudentContract.Effect, StudentContract.State>() {
    override fun createInitialState() = StudentContract.State()

    private val searchJob: Job? = null

    init {
        setEvent(StudentContract.Event.GetStudents)
    }

    override fun handleEvent(event: StudentContract.Event) {
        when(event) {
            is StudentContract.Event.GetStudents ->
                getStudents()
            is StudentContract.Event.AddStudent ->
                setEffect { StudentContract.Effect.NavigateToAddStudent }
            is StudentContract.Event.SearchStudent ->
                setState { copy(searchQuery = event.query) }
            is StudentContract.Event.NavigateToStudentProfile ->
                setEffect { StudentContract.Effect.NavigateToStudentProfile(event.id) }
        }
    }

    private fun getStudents() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            val studentFlow = repository.getStudents().cachedIn(viewModelScope)
            setState { copy(isLoading = false, students = studentFlow) }
        }
    }

//    private fun searchStudent(query: String) {
//        searchJob?.cancel()
//        searchJob = viewModelScope.launch {
//            setState { copy(isLoading = true) }
//            delay(500)
//            val studentFlow = repository.searchStudent(query).cachedIn(viewModelScope)
//            setState { copy(isLoading = false, students = studentFlow) }
//        }
//    }
}