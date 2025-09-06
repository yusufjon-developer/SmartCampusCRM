package com.smartcampus.crm.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class SubjectListItemDto(
    val id: Int,
    val name: String
)

@Serializable
data class SubjectDetailsDto(
    val id: Int,
    val name: String
)

@Serializable
data class SubjectCreateRequest(
    val name: String
)

@Serializable
data class SubjectUpdateRequest(
    val name: String?
)

@Serializable
data class SubjectDto(
    val id: Int,
    val name: String? = null
)
