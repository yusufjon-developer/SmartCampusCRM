package com.smartcampus.crm.domain.models

data class Grades(
    val id: Int,
    val date: String,
    val time: String,
    val student: StudentDetailsDto,
    val disciplines: Disciplines,
    val teachers: TeacherDetailsDto,
    val round: String,
    val mark: String
)
