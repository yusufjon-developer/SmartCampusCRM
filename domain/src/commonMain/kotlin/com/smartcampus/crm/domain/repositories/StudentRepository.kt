package com.smartcampus.crm.domain.repositories

import com.smartcampus.crm.domain.models.student.StudentInfo
import com.smartcampus.crm.domain.utils.RemoteWrapper

interface StudentRepository {
    fun getStudentInfo(id: Int): RemoteWrapper<StudentInfo>
}
