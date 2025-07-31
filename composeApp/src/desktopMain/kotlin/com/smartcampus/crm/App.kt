package com.smartcampus.crm

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.smartcampus.crm.navigation.Sidebar
import com.smartcampus.crm.navigation.menu.MainDrawerMenu
import com.smartcampus.crm.navigation.menu.TOP_DESTINATIONS
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    val homeNavController = rememberNavController()
    val settingsNavController = rememberNavController()
    val profileNavController = rememberNavController()
    val timetableNavController = rememberNavController()
    val employeesNavController = rememberNavController()
    var selectedTab by rememberSaveable { mutableStateOf<MainDrawerMenu>(MainDrawerMenu.Home) }

    Row(modifier = Modifier.fillMaxHeight().fillMaxWidth()) {
        // Левая боковая панель
        Sidebar(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        // Контент
        Column(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            when (selectedTab) {
                MainDrawerMenu.Home -> {}
                MainDrawerMenu.Profile -> Student(profileNavController)
                MainDrawerMenu.Settings -> {}
                MainDrawerMenu.Timetable -> {}
                MainDrawerMenu.Employees -> {}
            }
        }
    }
//    var selectedTab by rememberSaveable { mutableStateOf<MainDrawerMenu>(MainDrawerMenu.Home) }
//    val navController = when (selectedTab) {
//        MainDrawerMenu.Home -> homeNavController
//        MainDrawerMenu.Settings -> settingsNavController
//        MainDrawerMenu.Profile -> profileNavController
//        MainDrawerMenu.Timetable -> timetableNavController
//        MainDrawerMenu.Employees -> employeesNavController
//    }
//
//    val content: @Composable () -> Unit = when (selectedTab) {
//        MainDrawerMenu.Home -> {
//            {  }
//        }
//
//        MainDrawerMenu.Settings -> {
//            {  }
//        }
//
//        MainDrawerMenu.Profile -> {
//            { Student(profileNavController) }
//        }
//
//        MainDrawerMenu.Timetable -> {
//            { Student(profileNavController) }
//        }
//
//        MainDrawerMenu.Employees -> {
//            { Student(profileNavController) }
//        }
//    }
//
//    NavigationBar(
//        modifier = Modifier.padding(16.dp).fillMaxHeight()
//            .background(MaterialTheme.colorScheme.background)
//    ) {
//        Row {
//            Card(
//                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
//                shape = RoundedCornerShape(16.dp),
//                elevation = CardDefaults.elevatedCardElevation(8.dp),
//                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
//            ) {
//                TOP_DESTINATIONS.forEach { item ->
//                    NavigationDrawerItem(
//                        label = {
//                            Icon(
//                                painter = painterResource(item.icon),
//                                contentDescription = stringResource(item.label),
//                                tint = if (item.route == selectedTab) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary,
//                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
//                            )
//                        },
//                        modifier = Modifier.padding(16.dp),
//                        selected = item.route == selectedTab,
//                        onClick = {
//                            selectedTab = item.route
//                            //navController.navigate(item.route)
//                        })
//                }
//            }
//        }
//        content()
//    }

//
//        drawerContent = {
//            Column(modifier = Modifier.padding(16.dp)) {
//                Card(
//                    modifier = Modifier
//                        .padding(vertical = 8.dp)
//                        .clickable(
//                            enabled = true,
//                            onClick = navController::popBackStack
//                        ),
//                    shape = RoundedCornerShape(16.dp),
//                    elevation = CardDefaults.elevatedCardElevation(8.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
//                ) {
//                    Icon(
//                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                        contentDescription = null,
//                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
//                    )
//                }
//
//                Card(
//                    modifier = Modifier
//                        .padding(top = 16.dp, bottom = 16.dp)
//                        .fillMaxHeight(),
//                    shape = RoundedCornerShape(16.dp),
//                    elevation = CardDefaults.elevatedCardElevation(8.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
//                ) {
//                    repeat(10) { it ->
//                        NavigationDrawerItem(
//                            label = {
//                                Icon(
//                                    imageVector = Icons.Filled.Image,
//                                    contentDescription = null,
//                                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp)
//                                )
//                            },
//                            modifier = Modifier.padding(16.dp),
//                            selected = it == 1,
//                            onClick = {}
//                        )
//                    }
//                }
//            }
//        },
//        modifier = Modifier.fillMaxHeight()
//    ) {
//
//    }
}



