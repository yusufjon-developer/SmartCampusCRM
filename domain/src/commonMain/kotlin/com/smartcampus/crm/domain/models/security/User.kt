package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class User (
    val id: Int,
    val name: String
)