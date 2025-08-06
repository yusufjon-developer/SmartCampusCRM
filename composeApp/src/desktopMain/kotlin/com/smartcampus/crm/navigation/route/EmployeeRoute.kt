package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface EmployeeRoute {
    @Serializable data object Employee : EmployeeRoute
    @Serializable data object Teacher : EmployeeRoute
}