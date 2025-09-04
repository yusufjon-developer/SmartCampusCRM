package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface ScheduleRoute {
    @Serializable data object Schedule: ScheduleRoute
}