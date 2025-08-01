package com.smartcampus.presentation.ui.screen.studentProfile

import androidx.lifecycle.viewModelScope
import com.smartcampus.crm.domain.useCases.GetStudentInfoUseCase
import com.smartcampus.crm.domain.utils.Either
import com.smartcampus.presentation.core.base.BaseViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class StudentProfileViewModel(
    private val getStudentInfo: GetStudentInfoUseCase
) : BaseViewModel<StudentProfileContract.Event, StudentProfileContract.Effect, StudentProfileContract.State>() {
    override fun createInitialState() = StudentProfileContract.State()

    override fun handleEvent(event: StudentProfileContract.Event) {

        when (event) {
            is StudentProfileContract.Event.GetStudentInfo -> {
                getStudentInfo(event.id)
                    .onStart {
                        setState { copy(isLoading = true, studentInfo = null, error = null) }
                    }
                    .onEach { response ->
                        when (response) {
                            is Either.Left -> {
                                setState {
                                    copy(
                                        error = response.value.toString(),
                                        isLoading = false,
                                        studentInfo = null
                                    )
                                }
                            }

                            is Either.Right -> {
                                setState { copy(isLoading = false, studentInfo = response.value) }
                            }
                        }
                    }
                    .launchIn(viewModelScope)
            }
            is StudentProfileContract.Event.EditStudentInfo -> {
                // TODO
            }
        }
    }

}