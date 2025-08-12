package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.models.security.PermissionRequest
import com.smartcampus.crm.domain.repositories.SecurityRepository

class PermissionUseCases (
    private val repository: SecurityRepository
) {
    suspend fun getPermissionList(sortedBy: String?) = repository.getPermissionList(sortedBy)
    suspend fun getPermissionById(id: Int) = repository.getPermissionById(id = id)
    suspend fun createPermission(request: PermissionRequest) = repository.createPermission(request = request)
    suspend fun deletePermissionById(id: Int) = repository.deletePermissionById(id = id)
}