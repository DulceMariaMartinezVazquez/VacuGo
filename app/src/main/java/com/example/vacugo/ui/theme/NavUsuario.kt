package com.example.vacugo.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.vacugo.R
import com.example.vacugo.screens3.*

@Composable
fun MainScreen(navController: NavHostController) {
    val navItemList = listOf(
        NavItem(stringResource(R.string.home_screen),       Icons.Default.Home),
        NavItem(stringResource(R.string.historial_screen),  Icons.Default.List),
        NavItem(stringResource(R.string.calendario_screen), Icons.Default.DateRange),
        NavItem(stringResource(R.string.fila_screen),       Icons.Default.Face),
        NavItem(stringResource(R.string.expediente_screen), Icons.Default.Person)
    )

    var selectedIndex by remember { mutableStateOf(0) }
    var mostrarCentros by remember { mutableStateOf(false) }

    // Ocultar barra en Calendario (índice 2) y en pantalla de Centros
    val mostrarBarra = selectedIndex != 2 && !mostrarCentros

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Background,
        bottomBar = {
            if (mostrarBarra) {
                NavigationBar(containerColor = Surface) {
                    navItemList.forEachIndexed { index, navItem ->
                        NavigationBarItem(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index },
                            icon = {
                                Icon(
                                    imageVector = navItem.icon,
                                    contentDescription = navItem.label
                                )
                            },
                            label = { Text(navItem.label) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor   = Primary,
                                selectedTextColor   = Primary,
                                unselectedIconColor = TextHint,
                                unselectedTextColor = TextHint,
                                indicatorColor      = PrimaryLight
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        if (mostrarCentros) {
            CentrosScreen (
                modifier = Modifier.fillMaxSize(),
                onBack = { mostrarCentros = false }
            )
        } else {
            ContentScreen(
                navController = navController,
                modifier =
                    if (mostrarBarra)
                        Modifier.padding(innerPadding)
                    else
                        Modifier.fillMaxSize(),

                selectedIndex = selectedIndex,
                onNavigateBack = { selectedIndex = 0 },
                onVerCentros = { mostrarCentros = true }
            )
        }
    }
}

@Composable
fun ContentScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    onNavigateBack: () -> Unit = {},
    onVerCentros: () -> Unit = {}
) {

    when (selectedIndex) {

        0 -> HomeUsuarioScreen(modifier, navController)
        1 -> HistorialScreen(modifier, onNavigateBack = onNavigateBack)
        2 -> CalendarioScreen(modifier, onNavigateBack = onNavigateBack)
        3 -> FilaVirtualScreen(modifier, onVerOtrosCentros = onVerCentros)
        4 -> ExpedienteScreen(modifier)
    }
}