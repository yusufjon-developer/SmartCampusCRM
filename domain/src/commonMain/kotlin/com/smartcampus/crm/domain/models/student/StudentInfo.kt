package com.smartcampus.crm.domain.models.student

import kotlinx.serialization.Serializable

@Serializable
data class StudentInfo(
    val students: Student,
    val address: String = "г. Душанбе улица Пушкина дом Колотушкина",
    val passportNumber: String = "AB123456",
    val school: String = "Школа №14",
    val documentNumber: String = "AB1234555",
    val military: String = "Не служил",
    val studentCardNumber: String = "AB1234444",
    val studyType: String = "Бюджет",
    val studyForm: String = "Очная",
    val status: String = "Обучается",
    val fatherFIO: String = "Абдушахидов Юсуфджон Шарафджонович",
    val fatherPhone: String = "+992-915-15-15-15",
    val fatherAddress: String = "г. Душанбе улица Пушкина дом Колотушкина",
    val motherFIO: String = "Абдушахидова Амина Алихановна",
    val motherPhone: String = "+992-989-89-89-89",
    val motherAddress: String = "г. Душанбе улица Пушкина дом Колотушкина"
)
