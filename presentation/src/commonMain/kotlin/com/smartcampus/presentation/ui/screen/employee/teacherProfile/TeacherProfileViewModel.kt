package com.smartcampus.presentation.ui.screen.employee.teacherProfile

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.useCases.GetTeacherInfoByIdUseCase
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TeacherProfileViewModel(
    private val getTeacherInfo: GetTeacherInfoByIdUseCase
) : BaseViewModel<TeacherProfileContract.Event, TeacherProfileContract.Effect, TeacherProfileContract.State>() {
    override fun createInitialState() = TeacherProfileContract.State()

    override fun handleEvent(event: TeacherProfileContract.Event) {
        viewModelScope.launch {
            when (event) {
                is TeacherProfileContract.Event.GetTeacherInfo -> {
                    getTeacherInfo(event.id)
                        .onStart {
                            setState { copy(isLoading = true, teacherInfo = null, error = null) }
                        }
                        .onEach { response ->
                            when (response) {
                                is Either.Left -> {
                                    setState {
                                        copy(
                                            error = response.value.toString(),
                                            isLoading = false,
                                            teacherInfo = null
                                        )
                                    }
                                }

                                is Either.Right -> {
                                    setState {
                                        copy(
                                            isLoading = false,
                                            teacherInfo = response.value,
                                            error = null
                                        )
                                    }
                                }
                            }
                        }
                        .launchIn(viewModelScope)
                }

                is TeacherProfileContract.Event.EditTeacherInfo -> {
                    // TODO
                }
            }
        }
    }
}