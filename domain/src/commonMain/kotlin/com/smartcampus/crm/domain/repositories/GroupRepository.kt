package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.GroupCreateRequest
import com.smartcampus.crm.domain.models.GroupDto
import com.smartcampus.crm.domain.models.GroupUpdateRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface GroupRepository {
    suspend fun getGroupList(sortBy: String?): Flow<PagingData<GroupDto>>
    suspend fun getGroupById(id: Int): RemoteWrapper<GroupDto>
    suspend fun createGroup(request: GroupCreateRequest): RemoteWrapper<GroupDto>
    suspend fun updateGroup(id: Int, request: GroupUpdateRequest): RemoteWrapper<GroupDto>
    suspend fun deleteGroup(id: Int): RemoteWrapper<Unit>
}