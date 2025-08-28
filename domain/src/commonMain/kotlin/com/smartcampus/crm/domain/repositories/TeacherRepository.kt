package com.smartcampus.crm.domain.repositories

import com.smartcampus.crm.domain.models.teacher.Teacher
import com.smartcampus.crm.domain.models.teacher.TeacherInfo
import com.smartcampus.crm.domain.utils.RemoteWrapper

interface TeacherRepository {
    fun getTeacherList(): RemoteWrapper<List<Teacher>>
    fun getTeacherById(id: Int): RemoteWrapper<Teacher>
    fun getTeacherInfoById(id: Int): RemoteWrapper<TeacherInfo>
}