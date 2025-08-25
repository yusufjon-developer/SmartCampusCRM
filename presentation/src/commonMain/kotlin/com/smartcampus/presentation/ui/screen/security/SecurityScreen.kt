package com.smartcampus.presentation.ui.screen.security

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.smartcampus.presentation.core.components.cards.FeatureCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import smartcampuscrm.presentation.generated.resources.Res
import smartcampuscrm.presentation.generated.resources.ic_permission
import smartcampuscrm.presentation.generated.resources.ic_role
import smartcampuscrm.presentation.generated.resources.permission
import smartcampuscrm.presentation.generated.resources.role

@Composable
fun SecurityScreen(
    navigateToRole: () -> Unit,
    navigateToPermission: () -> Unit,
    viewModel: SecurityViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                SecurityContract.Effect.NavigateToRole -> navigateToRole()
                SecurityContract.Effect.NavigateToPermission -> navigateToPermission()
            }
        }
    }

    LazyVerticalGrid(
        modifier = Modifier.padding(8.dp),
        columns = GridCells.Adaptive(minSize = 196.dp)
    ) {
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_role),
                title = stringResource(Res.string.role),
                onClick = {
                    viewModel.setEvent(SecurityContract.Event.NavigateToRole)
                }
            )
        }
        item {
            FeatureCard(
                iconPainter = painterResource(Res.drawable.ic_permission),
                title = stringResource(Res.string.permission),
                onClick = {
                    viewModel.setEvent(SecurityContract.Event.NavigateToPermission)
                }
            )
        }
    }
}