package com.smartcampus.crm.di

import com.smartcampus.presentation.ui.screen.employee.EmployeeViewModel
import com.smartcampus.presentation.ui.screen.login.LoginViewModel
import com.smartcampus.presentation.ui.screen.settings.SettingsViewModel
import com.smartcampus.presentation.ui.screen.settings.theme.ThemeViewModel
import com.smartcampus.presentation.ui.screen.student.studentProfile.StudentProfileViewModel
import com.smartcampus.presentation.ui.screen.security.SecurityViewModel
import com.smartcampus.presentation.ui.screen.security.permission.PermissionViewModel
import com.smartcampus.presentation.ui.screen.security.role.RoleViewModel
import com.smartcampus.presentation.ui.screen.security.roleItem.RoleItemViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelsModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::StudentProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::SecurityViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::EmployeeViewModel)
    viewModelOf(::RoleViewModel)
    viewModelOf(::RoleItemViewModel)
    viewModelOf(::PermissionViewModel)
}