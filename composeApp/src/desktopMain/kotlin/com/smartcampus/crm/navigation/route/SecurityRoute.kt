package com.smartcampus.crm.navigation.route

import kotlinx.serialization.Serializable

@Serializable
sealed interface SecurityRoute {
    @Serializable data object Security
    @Serializable data object Role
    @Serializable data object Permission
    @Serializable data object User
    @Serializable data class UserPermission(val userId: Int)
    @Serializable data class RolePermission(val roleId: Int)
}