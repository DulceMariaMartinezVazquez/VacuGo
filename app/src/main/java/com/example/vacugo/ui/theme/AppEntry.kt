package com.example.vacugo.ui.theme

import androidx.compose.runtime.*
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
        2 -> MainScreen()
    }
}