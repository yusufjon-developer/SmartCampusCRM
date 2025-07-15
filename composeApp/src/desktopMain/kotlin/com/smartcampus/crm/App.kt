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
import com.smartcampus.presentationCore.theme.SmartCampusTheme
import com.smartcampus.presentationCore.theme.lightTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun App() {
    SmartCampusTheme {
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
        ColorPreview("primary", lightTheme.primary)
        ColorPreview("onPrimary", lightTheme.onPrimary)
        ColorPreview("primaryContainer", lightTheme.primaryContainer)
        ColorPreview("onPrimaryContainer", lightTheme.onPrimaryContainer)
        ColorPreview("inversePrimary", lightTheme.inversePrimary)

        ColorPreview("secondary", lightTheme.secondary)
        ColorPreview("onSecondary", lightTheme.onSecondary)
        ColorPreview("secondaryContainer", lightTheme.secondaryContainer)
        ColorPreview("onSecondaryContainer", lightTheme.onSecondaryContainer)

        ColorPreview("tertiary", lightTheme.tertiary)
        ColorPreview("onTertiary", lightTheme.onTertiary)
        ColorPreview("tertiaryContainer", lightTheme.tertiaryContainer)
        ColorPreview("onTertiaryContainer", lightTheme.onTertiaryContainer)

        ColorPreview("background", lightTheme.background)
        ColorPreview("onBackground", lightTheme.onBackground)
        ColorPreview("surface", lightTheme.surface)
        ColorPreview("onSurface", lightTheme.onSurface)
        ColorPreview("surfaceVariant", lightTheme.surfaceVariant)
        ColorPreview("onSurfaceVariant", lightTheme.onSurfaceVariant)
        ColorPreview("surfaceTint", lightTheme.surfaceTint)

        ColorPreview("inverseSurface", lightTheme.inverseSurface)
        ColorPreview("inverseOnSurface", lightTheme.inverseOnSurface)

        ColorPreview("error", lightTheme.error)
        ColorPreview("onError", lightTheme.onError)
        ColorPreview("errorContainer", lightTheme.errorContainer)
        ColorPreview("onErrorContainer", lightTheme.onErrorContainer)

        ColorPreview("outline", lightTheme.outline)
        ColorPreview("outlineVariant", lightTheme.outlineVariant)
        ColorPreview("scrim", lightTheme.scrim)

        ColorPreview("surfaceBright", lightTheme.surfaceBright)
        ColorPreview("surfaceContainer", lightTheme.surfaceContainer)
        ColorPreview("surfaceContainerHigh", lightTheme.surfaceContainerHigh)
        ColorPreview("surfaceContainerHighest", lightTheme.surfaceContainerHighest)
        ColorPreview("surfaceContainerLow", lightTheme.surfaceContainerLow)
        ColorPreview("surfaceContainerLowest", lightTheme.surfaceContainerLowest)
        ColorPreview("surfaceDim", lightTheme.surfaceDim)
    }
}
