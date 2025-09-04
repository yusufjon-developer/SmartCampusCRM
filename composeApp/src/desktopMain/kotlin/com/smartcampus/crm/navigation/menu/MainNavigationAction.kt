package com.smartcampus.crm.navigation.menu

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.employees
import smartcampuscrm.presentation.generated.resources.home
import smartcampuscrm.presentation.generated.resources.ic_employees
import smartcampuscrm.presentation.generated.resources.ic_home
import smartcampuscrm.presentation.generated.resources.ic_schedule
import smartcampuscrm.presentation.generated.resources.ic_security
import smartcampuscrm.presentation.generated.resources.ic_settings
import smartcampuscrm.presentation.generated.resources.ic_student
import smartcampuscrm.presentation.generated.resources.ic_teacher
import smartcampuscrm.presentation.generated.resources.profile
import smartcampuscrm.presentation.generated.resources.security
import smartcampuscrm.presentation.generated.resources.settings
import smartcampuscrm.presentation.generated.resources.timetable

data class NavBarItem(
    val route: MainDrawerMenu,
    val icon: DrawableResource,
    val selectedIcon: DrawableResource,
    val label: StringResource
)

val homeNavBar = NavBarItem(
    route = MainDrawerMenu.Home,
    icon = Res.drawable.ic_home,
    selectedIcon = Res.drawable.ic_home,
    label = Res.string.home
)

val settingsNavBar = NavBarItem(
    route = MainDrawerMenu.Settings,
    icon = Res.drawable.ic_settings,
    selectedIcon = Res.drawable.ic_settings,
    label = Res.string.settings
)

val profileNavBar = NavBarItem(
    route = MainDrawerMenu.Profile,
    icon = Res.drawable.ic_student,
    selectedIcon = Res.drawable.ic_student,
    label = Res.string.profile
)

val scheduleNavBar = NavBarItem(
    route = MainDrawerMenu.Schedule,
    icon = Res.drawable.ic_schedule,
    selectedIcon = Res.drawable.ic_schedule,
    label = Res.string.timetable
)

val employeesNavBar = NavBarItem(
    route = MainDrawerMenu.Employees,
    icon = Res.drawable.ic_employees,
    selectedIcon = Res.drawable.ic_employees,
    label = Res.string.employees
)

val securityNavBar = NavBarItem(
    route = MainDrawerMenu.Security,
    icon = Res.drawable.ic_security,
    selectedIcon = Res.drawable.ic_security,
    label = Res.string.security
)

val TOP_DESTINATIONS = listOf(
    homeNavBar,
    scheduleNavBar,
    employeesNavBar,
    profileNavBar,
    securityNavBar
)