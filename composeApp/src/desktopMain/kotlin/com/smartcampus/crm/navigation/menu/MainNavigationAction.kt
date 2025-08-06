package com.smartcampus.crm.navigation.menu

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.employees
import smartcampuscrm.presentation.generated.resources.home
import smartcampuscrm.presentation.generated.resources.ic_home
import smartcampuscrm.presentation.generated.resources.ic_settings
import smartcampuscrm.presentation.generated.resources.ic_student
import smartcampuscrm.presentation.generated.resources.ic_teacher
import smartcampuscrm.presentation.generated.resources.ic_timetable
import smartcampuscrm.presentation.generated.resources.profile
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

val timetableNavBar = NavBarItem(
    route = MainDrawerMenu.Timetable,
    icon = Res.drawable.ic_timetable,
    selectedIcon = Res.drawable.ic_timetable,
    label = Res.string.timetable
)

val employeesNavBar = NavBarItem(
    route = MainDrawerMenu.Employees,
    icon = Res.drawable.ic_teacher,
    selectedIcon = Res.drawable.ic_teacher,
    label = Res.string.employees
)

val TOP_DESTINATIONS = listOf(
    homeNavBar,
    timetableNavBar,
    employeesNavBar,
    profileNavBar,
)