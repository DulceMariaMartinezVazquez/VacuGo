package com.example.vacugo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vacugo.screens2.*
import com.example.vacugo.screens2.LoginScreen
import com.example.vacugo.screens2.NewPasswordScreen
import com.example.vacugo.screens2.RecoveryScreen
import com.example.vacugo.screens2.RegisterScreen
import com.example.vacugo.screens2.SupportScreen
import com.example.vacugo.screens2.VerifyCodeScreen
import com.example.vacugo.screens5.HomeAdminScreen
import com.example.vacugo.screens5.PersonalScreen
import com.example.vacugo.screens5.UsuariosScreen
import com.example.vacugo.ui.theme.VacuGoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Estado global para el tema claro/oscuro
            var isDarkTheme by remember { mutableStateOf(false) }
            
            VacuGoTheme(darkTheme = isDarkTheme) {
                AppNavigation(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {

        // PANTALLA DE LOGIN
        composable("login") {
            LoginScreen(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme,
                onNavigateToRegister = { navController.navigate("register") },
                onNavigateToRecovery = { navController.navigate("recovery") },
                onNavigateToSupport = { navController.navigate("support") },
                onLoginSuccess = { navController.navigate("usuarios/Paciente") },
                // Vínculos de Roles conectados a tus archivos reales
                onNavigateToAdmin = { navController.navigate("home_admin") },
                onNavigateToUser = { navController.navigate("usuarios/Paciente") },
                onNavigateToPersonal = { navController.navigate("personal/Doctor") }
            )
        }

        // VENTANAS DE DESTINO (Basado en tus imágenes)

        // 1. Administrador (en screens5)
        composable("home_admin") {
            HomeAdminScreen(navController, username = "Admin")
        }

        // 2. Usuario/Paciente (en screens5 según tu imagen, aunque se llame UsuariosScreen)
        composable("usuarios/{username}") { backStackEntry ->
            val user = backStackEntry.arguments?.getString("username") ?: "Usuario"
            UsuariosScreen( navController, user)
        }

        // 3. Doctor/Personal (en screens5)
        composable("personal/{username}") { backStackEntry ->
            val user = backStackEntry.arguments?.getString("username") ?: "Doctor"
            PersonalScreen(navController, user)
        }

        // FLUJO SECUNDARIO (screens2)
        composable("register") { RegisterScreen(onNavigateBack = { navController.popBackStack() }) }
        composable("recovery") {
            RecoveryScreen(
                onNavigateToVerify = { navController.navigate("verify") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("verify") {
            VerifyCodeScreen(
                onNavigateToNewPassword = { navController.navigate("new_password") },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("new_password") {
            NewPasswordScreen(
                onPasswordReset = {
                    navController.navigate("login") { popUpTo("login") { inclusive = true } }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
        composable("support") { SupportScreen(onNavigateBack = { navController.popBackStack() }) }
    }
}