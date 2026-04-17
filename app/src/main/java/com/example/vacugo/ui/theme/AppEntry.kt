package com.example.vacugo.ui.theme

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay

// Splash y Loading
import com.example.vacugo.screens1.SplashScreen
import com.example.vacugo.screens1.LoadingScreen

// Welcome y Config
import com.example.vacugo.screen.WelcomeScreen
import com.example.vacugo.screen.ConfigScreen
import com.example.vacugo.navigation.AppNavigation

@Composable
fun AppEntry(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {

    var screenState by remember {
        mutableStateOf(0)
    }

    // Splash → Loading
    LaunchedEffect(Unit) {

        delay(2000)
        screenState = 1

        delay(2000)
        screenState = 2
    }

    when (screenState) {

        // Splash
        0 -> SplashScreen()

        // Loading
        1 -> LoadingScreen()

        // Navegación principal
        2 -> {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "welcome"
            ) {


                composable("welcome") {

                    WelcomeScreen(

                        onNextClick = {
                            navController.navigate("config")
                        },

                        onLoginClick = {
                            navController.navigate("login_flow")
                        }

                    )

                }


                composable("config") {

                    ConfigScreen(

                        onStartClick = {
                            navController.navigate("login_flow")
                        }

                    )

                }


                composable("login_flow") {

                    AppNavigation(
                        isDarkTheme = isDarkTheme,
                        onToggleTheme = onToggleTheme
                    )

                }

            }

        }

    }

}