package com.smartcampus.presentation.ui.screen.employee

import com.smartcampus.presentation.core.base.BaseViewModel

class EmployeeViewModel(

) : BaseViewModel<EmployeeContract.Event, EmployeeContract.Effect, EmployeeContract.State>() {
    override fun createInitialState() = EmployeeContract.State()

    override fun handleEvent(event: EmployeeContract.Event) {
        when (event) {
            EmployeeContract.Event.NavigatToTeacher -> {
                setEffect { EmployeeContract.Effect.NavigateToTeacher }
            }
        }
    }
}