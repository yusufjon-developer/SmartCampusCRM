package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.models.security.RoleRequest
import com.smartcampus.crm.domain.repositories.SecurityRepository

class RoleUseCases(
    private val repository: SecurityRepository
) {
    suspend fun getRoleList(sortedBy: String?) = repository.getRoleList(sortedBy)
    suspend fun getRoleById(id: Int) = repository.getRoleById(id = id)
    suspend fun createRole(request: RoleRequest) = repository.createRole(request = request)
    suspend fun deleteRoleById(id: Int) = repository.deleteRoleById(id = id)
}