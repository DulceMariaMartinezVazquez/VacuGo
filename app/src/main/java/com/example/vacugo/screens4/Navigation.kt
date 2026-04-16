package com.example.vacugo.screens4

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object FilaVirtual : Screen("profesional")
    object Vacunacion : Screen("registro")
    object Estadisticas : Screen("estadisticas")
}