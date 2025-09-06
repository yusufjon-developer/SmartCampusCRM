package com.smartcampus.crm.domain.models

data class Attendance(
    val id: Int,
    val date: String,
    val time: String,
    val student: StudentDetailsDto,
    val disciplines: Disciplines,
    val mark: String
)
