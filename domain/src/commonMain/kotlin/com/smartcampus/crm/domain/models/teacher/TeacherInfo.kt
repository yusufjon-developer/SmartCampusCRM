package com.smartcampus.crm.domain.models.teacher

data class TeacherInfo(
    val teachers: Teacher,
    val address: String = "г. Душанбе улица Пушкина дом 12",
    val passportNumber: String = "AB123456",
    val highSchool: String = "Филиал МГУ в городе Душанбе",
    val documentNumber: String = "AB1234555",
    val military: String = "Служил",
    val degree: String = "Магистр",
    val title: String = "-",
    val position: String = "Старший преподаватель"
)
