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

        composable("usuarios/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            UsuariosScreen(navController, username)
        }

        composable("personal/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            PersonalScreen(navController, username)
        }

        composable("inventario/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            InventarioScreen(navController, username)
        }

        composable("altaStock/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            AltaStockScreen(navController, username)
        }

        composable("centros/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            CentrosScreen(navController, username)
        }

        composable("centroForm/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            CentroFormScreen(navController, username)
        }
    }
}