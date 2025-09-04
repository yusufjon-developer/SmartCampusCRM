package com.smartcampus.presentation.ui.screen.student.studentProfile

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.models.Role
import com.smartcampus.crm.domain.repositories.StudentRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import java.util.logging.Logger

class StudentProfileViewModel(
    private val repository: StudentRepository
) : BaseViewModel<StudentProfileContract.Event, StudentProfileContract.Effect, StudentProfileContract.State>() {

    override fun createInitialState() = StudentProfileContract.State()

    private val scope get() = viewModelScope

    override fun handleEvent(event: StudentProfileContract.Event) {

        when (event) {
            is StudentProfileContract.Event.AddStudent -> {
                repository.registerStudent(event.newStudent.copy(roleName = Role.Student.name))
                    .onStart {
                        setState { copy(isLoading = true) }
                    }
                    .onEach { result ->
                        when (result) {
                            is Either.Right -> {
                                setEffect {
                                    StudentProfileContract.Effect.ShowSuccessMessage("Студент успешно добавлен ✅")
                                }
                                setState { copy(isLoading = false) }
                            }

                            is Either.Left -> {
                                setEffect {
                                    StudentProfileContract.Effect.ShowErrorStudent(result.value)
                                }
                                setState { copy(isLoading = false) }
                            }
                        }
                    }
                    .launchIn(scope)
            }

            is StudentProfileContract.Event.GetStudent -> {
                repository.getStudent(event.id)
                    .onStart {
                        setState { copy(isLoading = true, student = null) }
                    }
                    .onEach { response ->
                        when (response) {
                            is Either.Left -> {
                                setState { copy(isLoading = false, student = null) }
                                setEffect { StudentProfileContract.Effect.ShowErrorStudent(response.value) }
                            }

                            is Either.Right -> {
                                setState { copy(isLoading = false, student = response.value) }
                            }
                        }
                    }
                    .launchIn(scope)
            }

            is StudentProfileContract.Event.GetStudentInfo -> {
                repository.getStudentInfo(event.id)
                    .onStart {
                        setState { copy(isLoading = true, studentInfo = null) }
                    }
                    .onEach { response ->
                        when (response) {
                            is Either.Left -> {
                                Logger.getLogger("StudentInfo").info(response.value.toString())
                                if (response.value == NetworkError.AccessDenied) {
                                    setState { copy(isLoading = false, studentInfo = null) }
                                    setEffect {
                                        StudentProfileContract.Effect.ShowErrorStudentInfo(response.value)
                                    }
                                }
                            }

                            is Either.Right -> {
                                Logger.getLogger("StudentInfo").info(response.value.toString())
                                setState { copy(isLoading = false, studentInfo = response.value) }
                            }
                        }
                    }
                    .launchIn(scope)
            }

            is StudentProfileContract.Event.UpdateStudent -> {
                val currentStudent = uiState.value.student
                if (currentStudent == null) {
                    setEffect {
                        StudentProfileContract.Effect.ShowErrorStudent(
                            NetworkError.Unexpected(
                                "Ошибка получения данных о студенте"
                            )
                        )
                    }
                    return
                }

                repository.updateStudentProfile(currentStudent.id, event.request)
                    .onStart { setState { copy(isLoading = true) } }
                    .onEach { result ->
                        when (result) {
                            is Either.Right -> {
                                setEffect { StudentProfileContract.Effect.ShowSuccessMessage(result.value) }
                                setState { copy(isLoading = false) }
                            }

                            is Either.Left -> {
                                setEffect { StudentProfileContract.Effect.ShowErrorStudent(result.value) }
                                setState { copy(isLoading = false) }
                            }
                        }
                    }
                    .launchIn(scope)
            }
        }
    }
}