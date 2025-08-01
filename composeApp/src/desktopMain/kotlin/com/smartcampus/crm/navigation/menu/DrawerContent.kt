package com.smartcampus.crm.navigation.menu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DrawerContent(
    selectedTab: MainDrawerMenu,
    onTabSelected: (MainDrawerMenu?) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp).width(IntrinsicSize.Min).fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            NavigationDrawerItem(
                label = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp)
                    )
                },
                onClick = onBackClick,
                selected = false,
                modifier = Modifier.padding(8.dp)
            )
        }


        Card(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            TOP_DESTINATIONS.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(item.icon),
                                contentDescription = stringResource(item.label),
                                tint = NavIconColor(selectedTab == item.route),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(24.dp)
                            )
                        }
                    },
                    selected = selectedTab == item.route,
                    onClick = {
                        onTabSelected(item.route)
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }

        }

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            NavigationDrawerItem(
                label = {
                    Icon(
                        painter = painterResource(settingsNavBar.icon),
                        contentDescription = stringResource(settingsNavBar.label),
                        tint = NavIconColor(selectedTab == MainDrawerMenu.Settings),
                        modifier = Modifier
                            .padding(4.dp)
                            .size(24.dp)
                    )
                },
                onClick = {
                    onTabSelected(MainDrawerMenu.Settings)
                },
                selected = selectedTab == MainDrawerMenu.Settings,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
private fun NavIconColor(selected: Boolean) =
    if (selected)
        MaterialTheme.colorScheme.secondary
    else
        MaterialTheme.colorScheme.onPrimary

