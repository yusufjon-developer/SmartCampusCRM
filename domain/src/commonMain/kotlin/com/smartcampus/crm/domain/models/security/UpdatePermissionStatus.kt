package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class UpdatePermissionStatus(
    val grantPermissionIds: Set<Int>,
    val revokePermissionIds: Set<Int>
)
