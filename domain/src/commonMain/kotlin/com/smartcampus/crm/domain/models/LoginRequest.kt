package com.smartcampus.crm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
    val uuid: String
)
