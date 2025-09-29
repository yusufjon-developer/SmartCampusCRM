package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.StudentDetailsDto
import com.smartcampus.crm.domain.models.StudentSensitiveDto
import com.smartcampus.crm.domain.models.StudentUpdateRequest
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.auth.RegisterResponse
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    fun getStudents(): Flow<PagingData<StudentDetailsDto>>
    fun getStudent(id: Int): RemoteWrapper<StudentDetailsDto>
    fun getStudentInfo(id: Int): RemoteWrapper<StudentSensitiveDto>

    fun updateStudentProfile(id: Int, student: StudentUpdateRequest): RemoteWrapper<String>
    fun registerStudent(request: RegisterRequest): RemoteWrapper<RegisterResponse>
}
