package com.smartcampus.crm.domain.repositories

import app.cash.paging.PagingData
import com.smartcampus.crm.domain.models.security.Role
import com.smartcampus.crm.domain.models.security.RolePermission
import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.models.security.UpdatePermissionStatus
import com.smartcampus.crm.domain.utils.RemoteWrapper
import kotlinx.coroutines.flow.Flow

interface RoleRepository {
    suspend fun getRoleList(sortBy: String?): Flow<PagingData<Role>>
    suspend fun getRoleById(id: Int): RemoteWrapper<RolePermission>
    suspend fun updateRolePermissions(id: Int, request: UpdatePermissionStatus): RemoteWrapper<RolePermission>
    suspend fun createRole(request: RoleRequest): RemoteWrapper<Role>
    suspend fun deleteRole(id: Int): RemoteWrapper<Unit>
}