package com.smartcampus.crm.domain.models

data class TeachersWorkload(
    val id: Int,
    val teachers: TeacherDetailsDto,
    val disciplines: Disciplines,
    val type: String,
    val hours: Int,
    val academicYear: String,
    val controlType: String,
    val group: GroupDto
)
