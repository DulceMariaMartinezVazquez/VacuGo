package com.example.vacugo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.vacugo.screens5.*

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeAdminScreen(navController, username = "Admin")
        }

        composable("Usuarios/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            UsuarioScreen(navController, username)
        }

        composable("Personal/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            PersonalScreen(navController, username)
        }

        composable("Inventario/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            InventarioScreen(navController, username)
        }

        composable("AltaStock/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            AltaStockScreen(navController, username)
        }

        composable("Centros/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            CentrosScreen(navController, username)
        }

        composable("CentroForm/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            CentroFormScreen(navController, username)
        }
    }
}