package com.smartcampus.crm.domain.models.student

import kotlinx.serialization.Serializable

@Serializable
data class StudentInfo(
    val address: String? = null,
    val passportNumber: String? = null,
    val school: String? = null,
    val documentNumber: String? = null,
    val military: String? = null,
    val studentCardNumber: String? = null,
    val studyType: String? = null,
    val studyForm: String? = null,
    val status: String? = null,
    val fatherFIO: String? = null,
    val fatherPhone: String? = null,
    val fatherAddress: String? = null,
    val motherFIO: String? = null,
    val motherPhone: String? = null,
    val motherAddress: String? = null
)
