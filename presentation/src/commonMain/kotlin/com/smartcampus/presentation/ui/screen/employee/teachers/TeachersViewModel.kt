package com.smartcampus.presentation.ui.screen.employee.teachers

import com.smartcampus.presentation.core.base.BaseViewModel

class TeachersViewModel(

) : BaseViewModel<TeachersContract.Event, TeachersContract.Effect, TeachersContract.State>() {
    override fun createInitialState() = TeachersContract.State()

    override fun handleEvent(event: TeachersContract.Event) {
        when (event) {
            TeachersContract.Event.NavigatToTeacher -> {
                setEffect { TeachersContract.Effect.NavigateToTeacher }
            }
        }
    }
}