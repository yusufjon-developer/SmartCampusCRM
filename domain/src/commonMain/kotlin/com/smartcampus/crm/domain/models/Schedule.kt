package com.smartcampus.crm.domain.models

import com.smartcampus.crm.domain.models.teacher.Teacher

data class Schedule(
    val id: Int,
    val day: String,
    val time: String,
    val groups: GroupDto,
    val disciplines: Disciplines,
    val teachers: Teacher,
    val Auditoriums: Auditoriums,
    val type: String
)
