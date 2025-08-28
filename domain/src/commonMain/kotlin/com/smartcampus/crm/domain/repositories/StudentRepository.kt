package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface StudentRepository {
    fun getStudents(): Flow<PagingData<Student>>
    fun getStudent(id: Int): RemoteWrapper<Student>
    fun getStudentInfo(id: Int): RemoteWrapper<StudentInfo>

    fun updateStudent(student: Student): RemoteWrapper<String>
    fun updateStudentInfo(studentInfo: StudentInfo): RemoteWrapper<String>
}
