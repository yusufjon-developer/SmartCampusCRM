package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.repositories.TeacherRepository

class GetTeacherInfoByIdUseCase(
    private val teacherRepository: TeacherRepository
) {
    operator fun invoke(id: Int) = teacherRepository.getTeacherInfoById(id)
}