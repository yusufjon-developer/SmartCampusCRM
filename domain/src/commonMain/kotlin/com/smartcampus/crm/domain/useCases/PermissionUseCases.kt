package com.smartcampus.crm.domain.useCases

import com.smartcampus.crm.domain.repositories.SecurityRepository

class PermissionUseCases (
    private val repository: SecurityRepository
) {
    suspend fun getPermissionList(sortedBy: String?) = repository.getPermissionList(sortedBy)
    suspend fun getPermissionById(id: Int) = repository.getPermissionById(id = id)
}