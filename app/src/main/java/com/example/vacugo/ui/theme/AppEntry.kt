package com.example.vacugo.ui.theme

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vacugo.screen.ConfigScreen
import com.example.vacugo.screen.WelcomeScreen
import kotlinx.coroutines.delay
import com.example.vacugo.screens1.LoadingScreen
import com.example.vacugo.screens1.SplashScreen


@Composable
fun AppEntry() {
    var screenState by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        delay(2000) // Splash
        screenState = 1
        delay(2000) // Loading
        screenState = 2
    }

    when (screenState) {
        0 -> SplashScreen()
        1 -> LoadingScreen()
        2 -> NavWelcome()
    }
}

@Composable
fun NavWelcome() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "welcome") {

        composable("welcome") {
            WelcomeScreen(
                onNextClick  = { navController.navigate("config") },
                onLoginClick = { navController.navigate("login") }
            )
        }

        composable("login") {
            LoginScreen(
                onLoginSuccess = { navController.navigate("config") }
            )
        }

        composable("config") {
            ConfigScreen(
                onStartClick = { /* navegar a home */ }
            )
        }

        // Agrega más pantallas aquí...
    }
}

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    TODO("Not yet implemented")
}



