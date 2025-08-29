package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserPermissions(
    val isActive: Boolean? = null,
    @SerialName("deviceId")
    val userDevices: Set<Int>? = null,
    @SerialName("updatePermissionsRequest")
    val permissions: UpdatePermissionStatus? = null,
)