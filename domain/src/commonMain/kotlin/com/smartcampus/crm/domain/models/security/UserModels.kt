package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserPermissions(
    val isActive: Boolean? = null,
    val userDevices: Set<UserDevice>? = null,
    val permissions: UpdatePermissionStatus? = null,
)