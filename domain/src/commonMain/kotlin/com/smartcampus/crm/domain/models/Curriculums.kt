package com.smartcampus.crm.domain.models

data class Curriculums(
    val id: Int,
    val specialities: Specialities,
    val year: Int,
    val profile: String,
    val educationForm: String,
    val degree: String,
    val duration: Int,
    val approvedDate: String
)
