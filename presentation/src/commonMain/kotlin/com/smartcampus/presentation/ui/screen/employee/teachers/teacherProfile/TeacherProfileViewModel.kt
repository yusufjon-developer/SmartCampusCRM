package com.smartcampus.presentation.ui.screen.employee.teachers.teacherProfile

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.models.Role
import com.smartcampus.crm.domain.models.TeacherUpdateRequest
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.repositories.TeacherRepository
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.crm.domain.utils.NetworkError
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import java.util.logging.Logger

class TeacherProfileViewModel(
    private val repository: TeacherRepository
) : BaseViewModel<TeacherProfileContract.Event, TeacherProfileContract.Effect, TeacherProfileContract.State>() {

    override fun createInitialState() = TeacherProfileContract.State()

    private val scope get() = viewModelScope

    override fun handleEvent(event: TeacherProfileContract.Event) {
        when (event) {
            is TeacherProfileContract.Event.GetTeacher -> fetchTeacher(event.id)
            is TeacherProfileContract.Event.GetTeacherInfo -> fetchTeacherInfo(event.id)
            is TeacherProfileContract.Event.AddTeacher -> createTeacher(event.newTeacher)
            is TeacherProfileContract.Event.UpdateTeacher -> updateTeacher(event.request)
            is TeacherProfileContract.Event.DeleteTeacher -> deleteTeacher(event.id)
        }
    }

    private fun fetchTeacher(id: Int) {
        scope.launch {
            repository.getTeacherById(id)
                .onStart { setState { copy(isLoading = true, teacher = null) } }
                .onEach { response ->
                    when (response) {
                        is Either.Left -> {
                            setState { copy(isLoading = false, teacher = null) }
                            setEffect { TeacherProfileContract.Effect.ShowErrorTeacher(response.value) }
                        }

                        is Either.Right -> {
                            setState { copy(isLoading = false, teacher = response.value) }
                        }
                    }
                }
                .launchIn(this)
        }
    }

    private fun fetchTeacherInfo(id: Int) {
        scope.launch {
            repository.getTeacherInfoById(id)
                .onStart { setState { copy(isLoading = true, teacherInfo = null) } }
                .onEach { response ->
                    when (response) {
                        is Either.Left -> {
                            Logger.getLogger("TeacherSensitiveDto").info(response.value.toString())
                            if (response.value != NetworkError.AccessDenied) {
                                setState { copy(isLoading = false, teacherInfo = null) }
                                setEffect { TeacherProfileContract.Effect.ShowErrorTeacherInfo(response.value) }
                            } else {
                                setState { copy(isLoading = false, teacherInfo = null) }
                            }
                        }

                        is Either.Right -> {
                            Logger.getLogger("TeacherSensitiveDto").info(response.value.toString())
                            setState { copy(isLoading = false, teacherInfo = response.value) }
                        }
                    }
                }
                .launchIn(this)
        }
    }

    private fun createTeacher(teacher: RegisterRequest) {
        scope.launch {
            repository.registerTeacher(teacher.copy(roleName = Role.Teacher.name))
                .onStart { setState { copy(isLoading = true) } }
                .onEach { result ->
                    when (result) {
                        is Either.Right -> {
                            setEffect {
                                TeacherProfileContract.Effect.ShowSuccessMessage("Преподаватель успешно добавлен ✅")
                            }
                            setState { copy(isLoading = false) }

                            val newId = result.value.teacherProfile?.id
                            if (newId != null) {
                                setEffect { TeacherProfileContract.Effect.AddTeacher(id = newId) }
                            }

                        }

                        is Either.Left -> {
                            setEffect { TeacherProfileContract.Effect.ShowErrorTeacher(result.value) }
                            setEffect { TeacherProfileContract.Effect.ShowErrorTeacherInfo(result.value) }
                            setState { copy(isLoading = false) }
                        }
                    }
                }
                .launchIn(this)
        }
    }

    private fun updateTeacher(request: TeacherUpdateRequest) {
        val current = uiState.value.teacher
        if (current == null) {
            setEffect {
                TeacherProfileContract.Effect.ShowErrorTeacher(
                    NetworkError.Unexpected(
                        "Ошибка получения данных преподавателя"
                    )
                )
            }
            return
        }

        scope.launch {
            repository.updateTeacher(current.id, request)
                .onStart { setState { copy(isLoading = true) } }
                .onEach { result ->
                    when (result) {
                        is Either.Right -> {
                            setEffect { TeacherProfileContract.Effect.ShowSuccessMessage("Данные преподавателя обновлены") }
                            setState { copy(isLoading = false) }
                            // автоматически обновляем данные
                            setEvent(TeacherProfileContract.Event.GetTeacher(current.id))
                        }

                        is Either.Left -> {
                            setEffect { TeacherProfileContract.Effect.ShowErrorTeacher(result.value) }
                            setEffect { TeacherProfileContract.Effect.ShowErrorTeacherInfo(result.value) }
                            setState { copy(isLoading = false) }
                        }
                    }
                }
                .launchIn(this)
        }
    }

    private fun deleteTeacher(id: Int) {
        scope.launch {
            repository.deleteTeacher(id)
                .onStart { setState { copy(isLoading = true) } }
                .onEach { result ->
                    when (result) {
                        is Either.Right -> {
                            setState { copy(isLoading = false) }
                            setEffect { TeacherProfileContract.Effect.TeacherDeleted }
                        }

                        is Either.Left -> {
                            setEffect { TeacherProfileContract.Effect.ShowErrorTeacher(result.value) }
                            setEffect { TeacherProfileContract.Effect.ShowErrorTeacherInfo(result.value) }
                            setState { copy(isLoading = false) }
                        }
                    }
                }
                .launchIn(this)
        }
    }
}
