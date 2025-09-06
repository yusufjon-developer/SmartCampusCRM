package com.smartcampus.crm.domain.models.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val username: String,
    val password: String,
    val email: String? = null,
    val fullName: String? = null,
    val roleName: String? = null, // "StudentDetailsDto" или "Teacher" или другой
    val studentProfile: StudentCreateRequest? = null,
    val studentInfo: StudentInfoCreateRequest? = null,
    val teacherProfile: TeacherCreateRequest? = null,
    val teacherInfo: TeacherInfoCreateRequest? = null
)