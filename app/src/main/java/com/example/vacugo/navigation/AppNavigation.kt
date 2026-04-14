package com.example.vacugo.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import com.example.vacugo.screens2.*
import com.example.vacugo.screens3.HomeScreen
import com.example.vacugo.screens5.HomeAdminScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigation(isDarkTheme: Boolean, onToggleTheme: () -> Unit) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // El Drawer solo se muestra en pantallas principales
    val showDrawer = currentDestination?.route in listOf("home_patient", "home_admin", "home_doctor")

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = showDrawer,
        drawerContent = {
            if (showDrawer) {
                ModalDrawerSheet {
                    Box(modifier = Modifier.fillMaxWidth().padding(24.dp)) {
                        Column {
                            Icon(Icons.Default.AccountCircle, null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Usuario VacuGo", style = MaterialTheme.typography.titleMedium)
                        }
                    }
                    HorizontalDivider()
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Home, null) },
                        label = { Text("Inicio") },
                        selected = currentDestination?.hierarchy?.any { it.route == "home_patient" } == true,
                        onClick = { 
                            scope.launch { drawerState.close() }
                            navController.navigate("home_patient") {
                                popUpTo("home_patient") { inclusive = true }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.Default.Map, null) },
                        label = { Text("Mapa") },
                        selected = currentDestination?.route == "home_doctor",
                        onClick = { 
                            scope.launch { drawerState.close() }
                            navController.navigate("home_doctor") 
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )

                    Spacer(modifier = Modifier.weight(1f))
                    HorizontalDivider()
                    
                    NavigationDrawerItem(
                        icon = { Icon(Icons.AutoMirrored.Filled.Logout, null) },
                        label = { Text("Cerrar Sesión") },
                        selected = false,
                        onClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate("login") {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                if (showDrawer) {
                    TopAppBar(
                        title = { Text("VacuGo") },
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menú")
                            }
                        }
                    )
                }
            }
        ) { padding ->
            Box(modifier = Modifier.padding(padding)) {
                NavHost(navController = navController, startDestination = "login") {
                    composable("login") {
                        LoginScreen(
                            isDarkTheme = isDarkTheme,
                            onToggleTheme = onToggleTheme,
                            onNavigateToRegister = { navController.navigate("register") },
                            onNavigateToRecovery = { navController.navigate("recovery") },
                            onNavigateToSupport = { navController.navigate("support") },
                            onLoginPatient = { navController.navigate("home_patient") },
                            onLoginDoctor = { navController.navigate("home_doctor") },
                            onLoginAdmin = { navController.navigate("home_admin") }
                        )
                    }
                    composable("register") {
                        RegisterScreen(onNavigateBack = { navController.popBackStack() })
                    }
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
                                navController.navigate("login") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable("support") {
                        SupportScreen(onNavigateBack = { navController.popBackStack() })
                    }
                    
                    composable("home_patient") { HomeScreen(navController) }
                    composable("home_admin") { HomeAdminScreen(navController) }
                    composable("home_doctor") { MapScreen { navController.popBackStack() } }
                }
            }
        }
    }
}
