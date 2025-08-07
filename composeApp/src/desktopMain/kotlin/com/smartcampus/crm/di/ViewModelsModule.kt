package com.smartcampus.crm.di

import com.smartcampus.presentation.ui.screen.employee.EmployeeViewModel
import com.smartcampus.presentation.ui.screen.login.LoginViewModel
import com.smartcampus.presentation.ui.screen.settings.SettingsViewModel
import com.smartcampus.presentation.ui.screen.settings.theme.ThemeViewModel
import com.smartcampus.presentation.ui.screen.studentProfile.StudentProfileViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelsModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::StudentProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::EmployeeViewModel)
}