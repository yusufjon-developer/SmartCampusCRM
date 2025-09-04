package com.smartcampus.crm.data.repositories

import com.smartcampus.crm.data.base.BaseRepository
import com.smartcampus.crm.data.remote.apiServices.ScheduleApiService
import com.smartcampus.crm.domain.models.ScheduleCreateRequest
import com.smartcampus.crm.domain.models.ScheduleDto
import com.smartcampus.crm.domain.models.ScheduleUpdateRequest
import com.smartcampus.crm.domain.repositories.ScheduleRepository
import com.smartcampus.crm.domain.utils.RemoteWrapper

class ScheduleRepositoryImpl(
    private val apiService: ScheduleApiService
) : ScheduleRepository, BaseRepository() {

    override suspend fun getSchedule(
        day: String,
        teacherId: Int?,
        groupId: Int?,
        auditoriumId: Int?
    ): RemoteWrapper<List<ScheduleDto>> =
        doRequest {
            apiService.getSchedule(day, teacherId, groupId, auditoriumId)
        }

    override suspend fun getScheduleById(id: Int): RemoteWrapper<ScheduleDto> =
        doRequest {
            apiService.getScheduleById(id)
        }

    override suspend fun createSchedule(request: ScheduleCreateRequest): RemoteWrapper<ScheduleDto> =
        doRequest {
            apiService.createSchedule(request)
        }

    override suspend fun updateSchedule(
        id: Int,
        request: ScheduleUpdateRequest
    ): RemoteWrapper<ScheduleDto> =
        doRequest {
            apiService.updateSchedule(id, request)
        }

    override suspend fun deleteSchedule(id: Int): RemoteWrapper<Unit> =
        doRequest {
            apiService.deleteSchedule(id)
        }

    override suspend fun validateRequest(request: ScheduleCreateRequest): RemoteWrapper<List<ScheduleDto>> =
        doRequest {
            apiService.validateRequest(request)
        }
}