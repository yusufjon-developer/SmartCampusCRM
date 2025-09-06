package com.smartcampus.crm.domain.repositories

import com.smartcampus.crm.domain.models.ScheduleCreateRequest
import com.smartcampus.crm.domain.models.ScheduleDto
import com.smartcampus.crm.domain.models.ScheduleUpdateRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper

interface ScheduleRepository {
    suspend fun getSchedule(
        day: String,
        teacherId: Int?,
        groupId: Int?,
        auditoriumId: Int?
    ): RemoteWrapper<List<ScheduleDto>>

    suspend fun getScheduleById(id: Int): RemoteWrapper<ScheduleDto>

    suspend fun createSchedule(request: ScheduleCreateRequest): RemoteWrapper<ScheduleDto>

    suspend fun updateSchedule(id: Int, request: ScheduleUpdateRequest): RemoteWrapper<ScheduleDto>

    suspend fun deleteSchedule(id: Int): RemoteWrapper<Unit>

    suspend fun validateRequest(request: ScheduleCreateRequest): RemoteWrapper<List<ScheduleDto>>
}