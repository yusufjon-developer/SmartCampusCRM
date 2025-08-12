package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class PermissionRequest(
    val name: String,
    val description: String
)
