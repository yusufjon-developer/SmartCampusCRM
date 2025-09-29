package com.smartcampus.crm.domain.models

data class Schedule(
    val id: Int,
    val day: String,
    val time: String,
    val groups: GroupDto,
    val disciplines: Disciplines,
    val teachers: TeacherDetailsDto,
    val Auditoriums: Auditoriums,
    val type: String
)
