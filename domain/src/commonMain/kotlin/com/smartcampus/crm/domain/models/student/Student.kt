package com.smartcampus.crm.domain.models.student

import com.smartcampus.crm.domain.models.Groups
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Int,
    val surname: String? = null,
    val name: String? = null,
    val lastname: String? = null,
    val birthday: String? = null,
    val groups: Groups? = null,
    val groupId: Int? = null,
    val email: String? = null,
    val phoneNumber: String? = null,
    val photo: String? = null
)
