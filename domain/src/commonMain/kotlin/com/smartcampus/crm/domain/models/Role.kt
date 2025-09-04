package com.smartcampus.crm.domain.models

enum class Role(val role: String) {
    Student("Студент"),
    Teacher("Учитель"),
    SystemAdmin("Администратор");
}