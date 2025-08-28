package com.smartcampus.crm.di

import com.smartcampus.presentation.ui.screen.employee.EmployeeViewModel
import com.smartcampus.presentation.ui.screen.login.LoginViewModel
import com.smartcampus.presentation.ui.screen.security.SecurityViewModel
import com.smartcampus.presentation.ui.screen.security.permission.PermissionViewModel
import com.smartcampus.presentation.ui.screen.security.role.RoleViewModel
import com.smartcampus.presentation.ui.screen.security.rolePermission.RolePermissionViewModel
import com.smartcampus.presentation.ui.screen.security.user.UserViewModel
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionViewModel
import com.smartcampus.presentation.ui.screen.settings.SettingsViewModel
import com.smartcampus.presentation.ui.screen.settings.theme.ThemeViewModel
import com.smartcampus.presentation.ui.screen.student.studentProfile.StudentProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelsModule = module {
    viewModelOf(::EmployeeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::PermissionViewModel)
    viewModelOf(::RolePermissionViewModel)
    viewModelOf(::RoleViewModel)
    viewModelOf(::SecurityViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::StudentProfileViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::UserViewModel)
    viewModelOf(::UserPermissionViewModel)
}