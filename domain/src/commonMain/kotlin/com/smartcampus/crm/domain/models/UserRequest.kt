package com.smartcampus.crm.domain.models

import com.smartcampus.crm.domain.repositories.DeviceManager
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private val deviceManager = DeviceManager.Base().invoke()

@Serializable
data class UserRequest(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("device_uuid")
    val deviceUuid: String = deviceManager.uuid,
    @SerialName("device_type")
    val deviceType: String = deviceManager.type
)
