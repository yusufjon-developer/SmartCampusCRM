package com.smartcampus.crm.domain.models

import com.smartcampus.crm.domain.models.student.Student

data class Attendance(
    val id: Int,
    val date: String,
    val time: String,
    val student: Student,
    val disciplines: Disciplines,
    val mark: String
)
