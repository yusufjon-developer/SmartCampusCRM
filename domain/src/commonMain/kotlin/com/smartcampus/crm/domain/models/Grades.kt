package com.smartcampus.crm.domain.models

import com.smartcampus.crm.domain.models.student.Student
import com.smartcampus.crm.domain.models.teacher.Teacher

data class Grades(
    val id: Int,
    val date: String,
    val time: String,
    val student: Student,
    val disciplines: Disciplines,
    val teachers: Teacher,
    val round: String,
    val mark: String
)
