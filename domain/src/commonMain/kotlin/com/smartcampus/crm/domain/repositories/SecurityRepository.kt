package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.security.Permission
import kotlinx.coroutines.flow.Flow

interface SecurityRepository {
    suspend fun getPermissionList(sortBy: String? = null): Flow<PagingData<Permission>>
}