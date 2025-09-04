package com.smartcampus.crm.domain.models.security

import kotlinx.serialization.Serializable

@Serializable
data class UserDevice(
    val id: Int,
    val deviceUuid: String,
    val isApprove: Boolean,
    val description: String?,
    val lastLoginAt: String,
    val registeredAt: String,
    val approvedAt: String?,
    val approvedBy: Int?
)
