package com.smartcampus.crm.domain.models

data class Groups(
    val id: Int,
    val name: String,
    val specialities: Specialities,
    val course: Int
)
