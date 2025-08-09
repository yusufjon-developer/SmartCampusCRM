package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class RoleRequest(
    val name: String,
    val description: String
)
