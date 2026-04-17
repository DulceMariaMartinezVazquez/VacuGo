package com.example.vacugo.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vacugo.NavDoctor

import com.example.vacugo.screens2.*
import com.example.vacugo.screens5.*
import com.example.vacugo.ui.theme.MainScreen

@Composable
fun AppNavigation(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        // LOGIN

        composable("login") {

            LoginScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,

                onNavigateToRegister = {
                    navController.navigate("register")
                },

                onNavigateToRecovery = {
                    navController.navigate("recovery")
                },

                onNavigateToSupport = {
                    navController.navigate("support")
                },

                onLoginSuccess = {
                    navController.navigate("usuarios/Paciente") {
                        popUpTo("login") {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },

                // ADMIN
                onNavigateToAdmin = {
                    navController.navigate("home_admin")
                },

                // PACIENTE
                onNavigateToUser = {
                    navController.navigate("usuarios/Paciente")
                },

                // DOCTOR
                onNavigateToPersonal = {
                    navController.navigate("doctor_home")
                }

            )
        }

        // ========================
        // ADMIN
        // ========================

        composable("home_admin") {

            HomeAdminScreen(
                navController,
                username = "Admin"
            )
        }

        composable("usuario/{username}") { backStackEntry ->

            val username =
                backStackEntry.arguments
                    ?.getString("username")
                    ?: "Admin"

            UsuarioScreen(navController, username)
        }

        composable("personal/{username}") { backStackEntry ->

            val username =
                backStackEntry.arguments
                    ?.getString("username")
                    ?: "Admin"

            PersonalScreen(navController, username)
        }

        composable("inventario/{username}") { backStackEntry ->

            val username =
                backStackEntry.arguments
                    ?.getString("username")
                    ?: "Admin"

            InventarioScreen(navController, username)
        }

        composable("centros/{username}") { backStackEntry ->

            val username =
                backStackEntry.arguments
                    ?.getString("username")

                    ?: "Admin"

            CentrosScreen(navController, username)
        }

        composable("altaStock/{username}") { backStackEntry ->

            val username =
                backStackEntry.arguments
                    ?.getString("username")
                    ?: "Admin"

            AltaStockScreen(navController, username)
        }

        composable("centroForm/{username}") { backStackEntry ->

            val username =
                backStackEntry.arguments
                    ?.getString("username")
                    ?: "Admin"

            CentroFormScreen(navController, username)
        }

        // ========================
        // PACIENTE
        // ========================

        composable("usuarios/{username}") { backStackEntry ->

            val username =
                backStackEntry.arguments?.getString("username")
                    ?: "Paciente"

            MainScreen( navController = navController)

        }

        // ========================
        // DOCTOR
        // ========================

        composable("doctor_home") {
            NavDoctor()
        }
        // REGISTER

        composable("register") {

            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // RECOVERY

        composable("recovery") {

            RecoveryScreen(
                onNavigateToVerify = {
                    navController.navigate("verify")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // VERIFY

        composable("verify") {

            VerifyCodeScreen(
                onNavigateToNewPassword = {
                    navController.navigate("new_password")
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // NEW PASSWORD

        composable("new_password") {

            NewPasswordScreen(
                onPasswordReset = {

                    navController.navigate("login") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                },

                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        // SUPPORT

        composable("support") {

            SupportScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

    }

}