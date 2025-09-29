package com.smartcampus.crm.domain.models.auth

import com.smartcampus.crm.domain.models.StudentDetailsDto
import com.smartcampus.crm.domain.models.StudentSensitiveDto
import com.smartcampus.crm.domain.models.TeacherDetailsDto
import com.smartcampus.crm.domain.models.TeacherSensitiveDto
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val userId: Int,
    val username: String,
    val role: String?,
    val studentProfile: StudentDetailsDto?,
    val studentSensitive: StudentSensitiveDto?,
    val teacherProfile: TeacherDetailsDto?,
    val teacherSensitive: TeacherSensitiveDto?,
)