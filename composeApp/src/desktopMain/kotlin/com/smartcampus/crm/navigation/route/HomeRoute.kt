package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface HomeRoute {
    @Serializable data object Home : HomeRoute
    @Serializable data object Auditorium : HomeRoute
    @Serializable data object Speciality : HomeRoute
    @Serializable data object Group : HomeRoute
    @Serializable data class StudentProfile(val studentId: Int) : HomeRoute
    @Serializable data class StudentGroup(val groupId: Int) : HomeRoute
}