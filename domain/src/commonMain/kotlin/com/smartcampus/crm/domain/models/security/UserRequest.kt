package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class UserRequest(
    val name: String
)
