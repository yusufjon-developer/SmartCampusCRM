package com.smartcampus.presentation.core.components.tabs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun TabStrip(
    tabs: List<String>,
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    tabContent: @Composable (tab: String, isSelected: Boolean, onClick: () -> Unit) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalAlignment
    ) {
        tabs.forEach { tab ->
            val isSelected = tab == selectedTab
            tabContent(
                tab,
                isSelected
            ) {
                if (!isSelected) onTabSelected(tab)
            }
        }
    }
}
