package com.smartcampus.crm.domain.models

data class CurriculumDisciplines(
    val id: Int,
    val curriculums: Curriculums,
    val disciplines: Disciplines,
    val semester: Int,
    val course: Int,
    val exam: Boolean,
    val credit: Boolean,
    val coursework: Boolean,
    val controlType: String,
    val credits: Int
)
