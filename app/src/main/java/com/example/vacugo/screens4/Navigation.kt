package com.example.vacugo.screens4

sealed class Screen(val route: String) {
    object Home : Screen("homee")
    object FilaVirtual : Screen("profesional")
    object Vacunacion : Screen("registro")
    object Estadisticas : Screen("estadisticas")
}