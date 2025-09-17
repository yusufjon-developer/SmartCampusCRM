package com.smartcampus.presentation.ui.screen.home

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.presentation.core.components.cards.FeatureCard
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.ic_home

@Composable
fun HomeScreen(
    onNavigatorToAuditorium: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                HomeContract.Effect.NavigateToAuditorium -> onNavigatorToAuditorium()
            }
        }
    }
    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Adaptive(minSize = 196.dp)
    ) {
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_home),
                title = "Аудитории",
                onClick = {
                    viewModel.setEvent(HomeContract.Event.NavigateToAuditorium)
                }
            )
        }
    }
}