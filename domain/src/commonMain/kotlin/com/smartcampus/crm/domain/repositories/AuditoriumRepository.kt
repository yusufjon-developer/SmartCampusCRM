package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.*
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface AuditoriumRepository {
    suspend fun getAuditoriumList(sortBy: String?): Flow<PagingData<AuditoriumDto>>
    suspend fun getAuditoriumById(id: Int): RemoteWrapper<AuditoriumDto>
    suspend fun createAuditorium(request: AuditoriumCreateRequest): RemoteWrapper<AuditoriumDto>
    suspend fun updateAuditorium(id: Int, request: AuditoriumUpdateRequest): RemoteWrapper<AuditoriumDto>
    suspend fun deleteAuditorium(id: Int): RemoteWrapper<Unit>
}