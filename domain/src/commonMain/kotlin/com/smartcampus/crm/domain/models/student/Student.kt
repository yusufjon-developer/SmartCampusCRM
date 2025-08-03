package com.smartcampus.crm.domain.models.student

import com.smartcampus.crm.domain.models.Groups
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val id: Int = 0,
    val surname: String = "Абдушахидов",
    val name: String = "Юсуфджон",
    val lastname: String = "Шарафджонович",
    val birthday: String = "02.01.2004",
    val groups: Groups,
    val email: String = "abdushakhidov@gmail.com",
    val phoneNumber: String = "+992-919-19-19-19",
    val photo: String = ""
)
