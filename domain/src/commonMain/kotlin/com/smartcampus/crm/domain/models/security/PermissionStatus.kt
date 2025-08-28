package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class PermissionStatus(
    val description: String,
    val hasPermission: Boolean,
    val id: Int,
    val name: String,
    val source: String
)