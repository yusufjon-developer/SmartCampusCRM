package com.smartcampus.presentation.ui.screen.employee.teachers.teacherProfile

import com.smartcampus.crm.domain.useCases.GetStudentInfoUseCase
import com.smartcampus.presentation.core.base.BaseViewModel
import com.smartcampus.presentation.ui.screen.employee.teachers.TeachersContract

class TeacherProfileViewModel (

) : BaseViewModel<TeacherProfileContract.Event, TeacherProfileContract.Effect, TeacherProfileContract.State>() {
    override fun createInitialState() = TeacherProfileContract.State()

    override fun handleEvent(event: TeacherProfileContract.Event) {
        // TODO
    }
}