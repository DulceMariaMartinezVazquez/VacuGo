package com.example.vacugo

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.example.vacugo.screens4.*

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        Triple(Screen.Home, stringResource(R.string.inicio7), Icons.Filled.Dashboard to Icons.Outlined.Dashboard),
        Triple(Screen.FilaVirtual, stringResource(R.string.fila_virtual1), Icons.Filled.People to Icons.Outlined.People),
        Triple(Screen.Vacunacion, stringResource(R.string.registro), Icons.Filled.Vaccines to Icons.Outlined.Vaccines),
        Triple(Screen.Estadisticas, stringResource(R.string.estadisticas1), Icons.Filled.BarChart to Icons.Outlined.BarChart)
    )

    val BrandBlue = Color(0xFF4F46E5)

    Scaffold(
        containerColor = Color.White,
        bottomBar = {
            Column {
                HorizontalDivider(color = Color(0xFFE5E7EB), thickness = 1.dp)
                NavigationBar(
                    containerColor = Color.White,
                    tonalElevation = 0.dp
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { (screen, label, icons) ->
                        val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = if (isSelected) icons.first else icons.second,
                                    contentDescription = label,
                                    modifier = Modifier.size(24.dp)
                                )
                            },
                            label = {
                                Text(text = label, fontSize = 10.sp)
                            },
                            selected = isSelected,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = BrandBlue,
                                selectedTextColor = BrandBlue,
                                unselectedIconColor = Color.Gray,
                                unselectedTextColor = Color.Gray,
                                indicatorColor = Color.Transparent
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) { HomeScreen() }
            composable(Screen.FilaVirtual.route) { ProfesionalScreen() }
            composable(Screen.Vacunacion.route) { RegistroScreen() }
            composable(Screen.Estadisticas.route) { EstadisticasScreen() }
        }
    }
}