package com.smartcampus.crm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Specialities(
    val id: Int,
    val name: String
)
