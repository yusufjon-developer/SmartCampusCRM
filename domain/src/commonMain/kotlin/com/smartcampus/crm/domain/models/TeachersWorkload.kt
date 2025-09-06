package com.smartcampus.crm.domain.models

import com.smartcampus.crm.domain.models.teacher.Teacher

data class TeachersWorkload(
    val id: Int,
    val teachers: Teacher,
    val disciplines: Disciplines,
    val type: String,
    val hours: Int,
    val academicYear: String,
    val controlType: String,
    val group: GroupDto
)
