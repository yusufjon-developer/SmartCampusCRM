package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.auth.RegisterRequest
import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.crm.domain.models.student.StudentUpdateRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    fun getStudents(): Flow<PagingData<Student>>
    fun getStudent(id: Int): RemoteWrapper<Student>
    fun getStudentInfo(id: Int): RemoteWrapper<StudentInfo>

    fun updateStudentProfile(id: Int, student: StudentUpdateRequest): RemoteWrapper<String>
    fun registerStudent(request: RegisterRequest): RemoteWrapper<String>
}
