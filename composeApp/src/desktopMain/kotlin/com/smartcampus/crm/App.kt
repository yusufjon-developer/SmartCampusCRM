package com.smartcampus.crm

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.smartcampus.crm.navigation.menu.DrawerContent
import com.smartcampus.crm.navigation.menu.MainDrawerMenu
import com.smartcampus.crm.navigation.safelyPopBackStack
import com.smartcampus.crm.navigator.EmployeeNavigator
import com.smartcampus.crm.navigator.HomeNavigator
import com.smartcampus.crm.navigator.SecurityNavigator
import com.smartcampus.crm.navigator.SettingsNavigator
import com.smartcampus.crm.navigator.StudentNavigator

@Composable
fun App() {
    val homeNavController = rememberNavController()
    val settingsNavController = rememberNavController()
    val profileNavController = rememberNavController()
    val timetableNavController = rememberNavController()
    val employeesNavController = rememberNavController()
    val securityNavController = rememberNavController()

    var selectedTab by rememberSaveable { mutableStateOf<MainDrawerMenu>(MainDrawerMenu.Home) }

    val navController = when (selectedTab) {
        MainDrawerMenu.Home -> homeNavController
        MainDrawerMenu.Settings -> settingsNavController
        MainDrawerMenu.Profile -> profileNavController
        MainDrawerMenu.Timetable -> timetableNavController
        MainDrawerMenu.Employees -> employeesNavController
        MainDrawerMenu.Security -> securityNavController
    }

    val stateHolder = rememberSaveableStateHolder()

    PermanentNavigationDrawer(
        drawerContent = {
            DrawerContent(
                selectedTab = selectedTab,
                onTabSelected = { tab -> tab?.let { selectedTab = it } },
                onBackClick = navController::safelyPopBackStack
            )
        },
        modifier = Modifier.fillMaxSize()
    ) {
        stateHolder.SaveableStateProvider(key = selectedTab) {
            when (selectedTab) {
                MainDrawerMenu.Home -> HomeNavigator(homeNavController)
                MainDrawerMenu.Profile -> StudentNavigator(profileNavController)
                MainDrawerMenu.Settings -> SettingsNavigator(settingsNavController)
                MainDrawerMenu.Employees -> EmployeeNavigator(employeesNavController)
                MainDrawerMenu.Security -> SecurityNavigator(securityNavController)
                else -> {}
            }
        }
    }
}