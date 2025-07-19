package com.smartcampus.crm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val token: String,
    val tokenType: String,
)