package com.smartcampus.presentation.ui.screen.student.studentProfile

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.repositories.StudentRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class StudentProfileViewModel(
    private val repository: StudentRepository
) : BaseViewModel<StudentProfileContract.Event, StudentProfileContract.Effect, StudentProfileContract.State>() {
    override fun createInitialState() = StudentProfileContract.State()

    override fun handleEvent(event: StudentProfileContract.Event) {

        when (event) {
            StudentProfileContract.Event.AddStudent ->
                setEffect { StudentProfileContract.Effect.AddStudent }

            is StudentProfileContract.Event.GetStudent -> {
                repository.getStudent(event.id)
                    .onStart {
                        setState { copy(isLoading = true, student = null, error = null) }
                    }
                    .onEach { response ->
                        when (response) {
                            is Either.Left -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        error = response.value.toString(),
                                        student = null
                                    )
                                }
                            }

                            is Either.Right -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        student = response.value,
                                        error = null
                                    )
                                }
                            }
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is StudentProfileContract.Event.GetStudentInfo -> {
                repository.getStudentInfo(event.id)
                    .onStart {
                        setState { copy(isLoading = true, studentInfo = null, error = null) }
                    }
                    .onEach { response ->
                        when (response) {
                            is Either.Left -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        error = response.value.toString(),
                                        studentInfo = null
                                    )
                                }
                            }

                            is Either.Right -> {
                                setState {
                                    copy(
                                        isLoading = false,
                                        studentInfo = response.value,
                                        error = null,
                                    )
                                }
                            }
                        }
                    }
                    .launchIn(viewModelScope)
            }

            is StudentProfileContract.Event.UpdateStudent -> {

            }

            is StudentProfileContract.Event.UpdateStudentInfo -> {}
        }
    }

}