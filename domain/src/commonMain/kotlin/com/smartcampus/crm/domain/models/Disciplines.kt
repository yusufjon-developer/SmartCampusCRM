package com.smartcampus.crm.domain.models

data class Disciplines(
    val id: Int,
    val subjects: Subjects,
    val semester: Int,
    val specialities: Specialities,
    val course: Int,
    val lecture: Int,
    val practice: Int,
    val laboratory: Int,
    val seminar: Int,
    val control: String
)
