package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.crm.domain.models.TeacherSensitiveDto
import com.smartcampus.crm.domain.models.TeacherUpdateRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface TeacherRepository {
    suspend fun getTeacherList(sortBy: String?): Flow<PagingData<TeacherDetailsDto>>
    suspend fun getTeacherById(id: Int): RemoteWrapper<TeacherDetailsDto>
    suspend fun getTeacherInfoById(id: Int): RemoteWrapper<TeacherSensitiveDto>

    suspend fun updateTeacher(id: Int, request: TeacherUpdateRequest): RemoteWrapper<TeacherDetailsDto>
    suspend fun deleteTeacher(id: Int): RemoteWrapper<Unit>
}