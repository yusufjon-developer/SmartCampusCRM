package com.smartcampus.crm.domain.models.teacher

data class Teacher (
    val id: Int = 0,
    val surname: String = "Бобоев",
    val name: String = "Шараф",
    val lastname: String = "Асрорович",
    val birthday: String = "02.01.1989",
    val email: String = "boboev@gmail.com",
    val phoneNumber: String = "+992-919-91-91-91",
    val photo: String = ""
)