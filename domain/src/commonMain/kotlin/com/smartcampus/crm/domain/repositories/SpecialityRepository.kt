package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.SpecialityCreateRequest
import com.smartcampus.crm.domain.models.SpecialityDto
import com.smartcampus.crm.domain.models.SpecialityUpdateRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface SpecialityRepository {
    suspend fun getSpecialityList(sortBy: String?): Flow<PagingData<SpecialityDto>>
    suspend fun getSpecialityById(id: Int): RemoteWrapper<SpecialityDto>
    suspend fun createSpeciality(request: SpecialityCreateRequest): RemoteWrapper<SpecialityDto>
    suspend fun updateSpeciality(id: Int, request: SpecialityUpdateRequest): RemoteWrapper<SpecialityDto>
    suspend fun deleteSpeciality(id: Int): RemoteWrapper<Unit>
}