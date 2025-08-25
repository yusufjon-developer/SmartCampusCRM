package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class RoleItem(
    val roleId: Int,
    val name: String,
    val description: String,
    val permissions: List<PermissionStatus>,
)