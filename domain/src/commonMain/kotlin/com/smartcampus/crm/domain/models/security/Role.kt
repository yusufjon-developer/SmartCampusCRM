package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class Role(
    val id: Int,
    val name: String,
    val description: String
)
