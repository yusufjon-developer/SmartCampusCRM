package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.repositories.StudentRepository

class GetStudentInfoUseCase(
    private val studentRepository: StudentRepository
) {
    operator fun invoke(id: Int) = studentRepository.getStudentInfo(id)
}