package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class UserPermission(
    val userId: Int,
    val username: String,
    val isActive: Boolean,
    val userDevices: List<UserDevice>,
    val permissions: List<PermissionStatus>,
)