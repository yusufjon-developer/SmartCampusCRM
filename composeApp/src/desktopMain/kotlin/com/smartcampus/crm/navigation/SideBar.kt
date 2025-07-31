package com.smartcampus.crm.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.crm.navigation.menu.MainDrawerMenu
import com.smartcampus.crm.navigation.menu.TOP_DESTINATIONS
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Sidebar(
    selectedTab: MainDrawerMenu,
    onTabSelected: (MainDrawerMenu) -> Unit
) {
    Card(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxHeight()
            .width(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column {
            TOP_DESTINATIONS.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = stringResource(item.label),
                            tint = if (item.route == selectedTab)
                                MaterialTheme.colorScheme.secondary
                            else
                                MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.padding(8.dp)
                        )
                    },
                    selected = item.route == selectedTab,
                    onClick = {
                        onTabSelected(item.route)
                    },
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
