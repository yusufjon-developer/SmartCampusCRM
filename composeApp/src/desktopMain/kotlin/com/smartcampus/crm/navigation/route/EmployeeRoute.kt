package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface EmployeeRoute {
    @Serializable data object Employee : EmployeeRoute
    @Serializable data object Teachers : EmployeeRoute
    @Serializable data object Administration : EmployeeRoute
    @Serializable data object ITDepartment : EmployeeRoute
    @Serializable data class TeacherProfile(val teacherId: Int?) : EmployeeRoute
}