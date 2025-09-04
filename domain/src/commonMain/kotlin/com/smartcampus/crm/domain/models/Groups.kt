package com.smartcampus.crm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class Groups(
    val id: Int,
    val name: String? = null,
    val specialities: Specialities? = null,
    val course: Int? = null
)
