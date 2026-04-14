package com.example.vacugo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.vacugo.screens2.LoginScreen
import com.example.vacugo.screens5.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "welcome"
    ) {
        // PANTALLA DE LOGIN
        composable("login") {
            LoginScreen(
                isDarkTheme = false,
                onToggleTheme = { /* Lógica de tema */ },
                onNavigateToRegister = { /* navController.navigate("registro") */ },
                onNavigateToRecovery = { /* navController.navigate("recuperar") */ },
                onNavigateToSupport = { /* navController.navigate("soporte") */ },
                onLoginSuccess = { navController.navigate("home") },
                // CONEXIÓN DE LOS ROLES
                onNavigateToAdmin = { navController.navigate("home") },
                onNavigateToUser = { navController.navigate("usuarios/Paciente") },
                onNavigateToPersonal = { navController.navigate("personal/Doctor") }
            )
        }

        // VISTA ADMINISTRADOR
        composable("home") {
            HomeAdminScreen(navController, username = "Admin")
        }

        // VISTA USUARIOS / PACIENTES
        composable("usuarios/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Usuario"
            UsuariosScreen(navController, username)
        }

        // VISTA PERSONAL / DOCTOR
        composable("personal/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Personal"
            PersonalScreen(navController, username)
        }

        // Rutas adicionales de tu sistema (Inventario, Centros, etc.)
        composable("inventario/{username}") { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: "Admin"
            InventarioScreen(navController, username)
        }

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