package com.smartcampus.crm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.smartcampus.crm.navigation.Route
import com.smartcampus.crm.ui.LoginScreen
import com.smartcampus.crm.ui.MainScreen
import com.smartcampus.crm.ui.RegistrationScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.SignIn
    ) {
        composable<Route.SignIn> {
            LoginScreen(
                navigateToMain = { test ->
                    navController.navigate(Route.Main(test = test))
                },

                navigateToRegistration = {
                    navController.navigate(Route.SignUp)
                }
            )
        }

        composable<Route.SignUp> {
            RegistrationScreen(
                navigateToLogin = {
                    navController.navigate(Route.SignIn)
                }
            )
        }

        composable<Route.Main> { entry ->
            val args = entry.toRoute<Route.Main>()
            MainScreen(args.test)
        }
    }

    LightThemeColorPreview()
}


@Composable
fun ColorPreview(name: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(color)
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            color = if (color.luminance() < 0.5f) Color.White else Color.Black
        )
    }
}

@Preview
@Composable
fun LightThemeColorPreview() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        ColorPreview("primary", MaterialTheme.colorScheme.primary)
        ColorPreview("onPrimary", MaterialTheme.colorScheme.onPrimary)
        ColorPreview("primaryContainer", MaterialTheme.colorScheme.primaryContainer)
        ColorPreview("onPrimaryContainer", MaterialTheme.colorScheme.onPrimaryContainer)
        ColorPreview("inversePrimary", MaterialTheme.colorScheme.inversePrimary)

        ColorPreview("secondary", MaterialTheme.colorScheme.secondary)
        ColorPreview("onSecondary", MaterialTheme.colorScheme.onSecondary)
        ColorPreview("secondaryContainer", MaterialTheme.colorScheme.secondaryContainer)
        ColorPreview("onSecondaryContainer", MaterialTheme.colorScheme.onSecondaryContainer)

        ColorPreview("tertiary", MaterialTheme.colorScheme.tertiary)
        ColorPreview("onTertiary", MaterialTheme.colorScheme.onTertiary)
        ColorPreview("tertiaryContainer", MaterialTheme.colorScheme.tertiaryContainer)
        ColorPreview("onTertiaryContainer", MaterialTheme.colorScheme.onTertiaryContainer)

        ColorPreview("background", MaterialTheme.colorScheme.background)
        ColorPreview("onBackground", MaterialTheme.colorScheme.onBackground)
        ColorPreview("surface", MaterialTheme.colorScheme.surface)
        ColorPreview("onSurface", MaterialTheme.colorScheme.onSurface)
        ColorPreview("surfaceVariant", MaterialTheme.colorScheme.surfaceVariant)
        ColorPreview("onSurfaceVariant", MaterialTheme.colorScheme.onSurfaceVariant)
        ColorPreview("surfaceTint", MaterialTheme.colorScheme.surfaceTint)

        ColorPreview("inverseSurface", MaterialTheme.colorScheme.inverseSurface)
        ColorPreview("inverseOnSurface", MaterialTheme.colorScheme.inverseOnSurface)

        ColorPreview("error", MaterialTheme.colorScheme.error)
        ColorPreview("onError", MaterialTheme.colorScheme.onError)
        ColorPreview("errorContainer", MaterialTheme.colorScheme.errorContainer)
        ColorPreview("onErrorContainer", MaterialTheme.colorScheme.onErrorContainer)

        ColorPreview("outline", MaterialTheme.colorScheme.outline)
        ColorPreview("outlineVariant", MaterialTheme.colorScheme.outlineVariant)
        ColorPreview("scrim", MaterialTheme.colorScheme.scrim)

        ColorPreview("surfaceBright", MaterialTheme.colorScheme.surfaceBright)
        ColorPreview("surfaceContainer", MaterialTheme.colorScheme.surfaceContainer)
        ColorPreview("surfaceContainerHigh", MaterialTheme.colorScheme.surfaceContainerHigh)
        ColorPreview("surfaceContainerHighest", MaterialTheme.colorScheme.surfaceContainerHighest)
        ColorPreview("surfaceContainerLow", MaterialTheme.colorScheme.surfaceContainerLow)
        ColorPreview("surfaceContainerLowest", MaterialTheme.colorScheme.surfaceContainerLowest)
        ColorPreview("surfaceDim", MaterialTheme.colorScheme.surfaceDim)
    }
}
