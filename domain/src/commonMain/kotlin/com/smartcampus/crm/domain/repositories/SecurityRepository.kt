package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.security.Permission
import com.smartcampus.crm.domain.models.security.Role
import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface SecurityRepository {
    suspend fun getRoleList(sortBy: String? = null): Flow<PagingData<Role>>
    suspend fun createRole(request: RoleRequest): RemoteWrapper<Role>
    suspend fun deleteRoleById(id: Int): RemoteWrapper<Boolean>
    suspend fun getRoleById(id: Int): RemoteWrapper<Role>

    suspend fun getPermissionList(sortBy: String? = null): Flow<PagingData<Permission>>
    suspend fun getPermissionById(id: Int): RemoteWrapper<Permission>
}