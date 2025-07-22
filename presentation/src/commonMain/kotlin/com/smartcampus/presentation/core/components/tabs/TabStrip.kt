package com.smartcampus.presentation.core.components.tabs

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TabStrip(
    tabs: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    tabContent: @Composable (tab: String, isSelected: Boolean, onClick: () -> Unit) -> Unit
) {
    Row {
        tabs.forEach { tab ->
            val selected = selectedTab == tab
            tabContent(
                tab,
                selected
            ) {
                onTabSelected(tab)
            }
        }

    }
}