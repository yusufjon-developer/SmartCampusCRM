package com.smartcampus.crm

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.smartcampus.crm.navigation.Route
import com.smartcampus.presentation.core.ThemeAndComponentPreview
import com.smartcampus.presentation.ui.screen.MainScreen
import com.smartcampus.presentation.ui.screen.login.LoginScreen
import com.smartcampus.presentation.ui.screen.registration.RegistrationScreen
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

    ThemeAndComponentPreview()
}



