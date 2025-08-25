package com.smartcampus.presentation.ui.screen.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.presentation.core.components.cards.FeatureCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.ic_logout
import smartcampuscrm.presentation.generated.resources.ic_theme
import smartcampuscrm.presentation.generated.resources.logout
import smartcampuscrm.presentation.generated.resources.theme

@Composable
fun SettingsScreen(
    navigateToTheme: () -> Unit,
    viewModel: SettingsViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SettingsContract.Effect.NavigateToTheme -> navigateToTheme()
            }
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Adaptive(minSize = 196.dp)
    ) {
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_theme),
                title = stringResource(Res.string.theme),
                onClick = {
                    viewModel.setEvent(SettingsContract.Event.NavigateToTheme)
                }
            )
        }
        item {
            FeatureCard(
                background = MaterialTheme.colorScheme.surfaceVariant,
                iconPainter = painterResource(Res.drawable.ic_logout),
                contentColor = MaterialTheme.colorScheme.error,
                title = stringResource(Res.string.logout),
                onClick = {
                    viewModel.setEvent(SettingsContract.Event.Logout)
                }
            )
        }
    }
}