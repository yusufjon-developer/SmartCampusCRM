package com.smartcampus.crm.di

import com.smartcampus.presentation.ui.screen.employee.EmployeeViewModel
import com.smartcampus.presentation.ui.screen.employee.teachers.TeachersViewModel
import com.smartcampus.presentation.ui.screen.employee.teachers.teacherProfile.TeacherProfileViewModel
import com.smartcampus.presentation.ui.screen.login.LoginViewModel
import com.smartcampus.presentation.ui.screen.schedule.ScheduleViewModel
import com.smartcampus.presentation.ui.screen.security.SecurityViewModel
import com.smartcampus.presentation.ui.screen.security.permission.PermissionViewModel
import com.smartcampus.presentation.ui.screen.security.role.RoleViewModel
import com.smartcampus.presentation.ui.screen.security.rolePermission.RolePermissionViewModel
import com.smartcampus.presentation.ui.screen.security.user.UserViewModel
import com.smartcampus.presentation.ui.screen.security.userPermission.UserPermissionViewModel
import com.smartcampus.presentation.ui.screen.settings.SettingsViewModel
import com.smartcampus.presentation.ui.screen.settings.theme.ThemeViewModel
import com.smartcampus.presentation.ui.screen.student.StudentViewModel
import com.smartcampus.presentation.ui.screen.student.studentProfile.StudentProfileViewModel
import com.smartcampus.presentation.ui.widgets.auditoriumDropdown.AuditoriumDropdownViewModel
import com.smartcampus.presentation.ui.widgets.groupDropdown.GroupDropdownViewModel
import com.smartcampus.presentation.ui.widgets.teacherDropdown.TeacherDropdownViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelsModule = module {
    viewModelOf(::EmployeeViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::StudentViewModel)
    viewModelOf(::StudentProfileViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::PermissionViewModel)
    viewModelOf(::RolePermissionViewModel)
    viewModelOf(::RoleViewModel)
    viewModelOf(::SecurityViewModel)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::StudentProfileViewModel)
    viewModelOf(::ThemeViewModel)
    viewModelOf(::UserViewModel)
    viewModelOf(::UserPermissionViewModel)
    viewModelOf(::ScheduleViewModel)
    viewModelOf(::GroupDropdownViewModel)
    viewModelOf(::AuditoriumDropdownViewModel)
    viewModelOf(::TeacherDropdownViewModel)
    viewModelOf(::TeachersViewModel)
    viewModelOf(::TeacherProfileViewModel)
}