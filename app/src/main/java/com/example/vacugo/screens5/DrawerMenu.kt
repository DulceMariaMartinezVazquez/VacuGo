package com.example.vacugo.screens5

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.vacugo.R

@Composable
fun DrawerMenu(
    username: String?,
    onHome: () -> Unit,
    onUsuarios: () -> Unit,
    onPersonal: () -> Unit,
    onInventario: () -> Unit,
    onAltaStock: () -> Unit,
    onCentros: () -> Unit,
    onAjustes: () -> Unit,
    onLogout: () -> Unit
) {

    ModalDrawerSheet {

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_inicio)) },
            selected = false,
            onClick = onHome,
            icon = { Icon(Icons.Default.Home, null) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_usuarios)) },
            selected = false,
            onClick = onUsuarios,
            icon = { Icon(Icons.Default.People, null) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_personal)) },
            selected = false,
            onClick = onPersonal,
            icon = { Icon(Icons.Default.Person, null) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_inventario)) },
            selected = false,
            onClick = onInventario,
            icon = { Icon(Icons.Default.Inventory, null) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_alta_stock)) },
            selected = false,
            onClick = onAltaStock,
            icon = { Icon(Icons.Default.Add, null) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_centros)) },
            selected = false,
            onClick = onCentros,
            icon = { Icon(Icons.Default.LocationOn, null) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_ajustes)) },
            selected = false,
            onClick = onAjustes,
            icon = { Icon(Icons.Default.Settings, null) }
        )

        NavigationDrawerItem(
            label = { Text(stringResource(R.string.menu_logout)) },
            selected = false,
            onClick = onLogout,
            icon = { Icon(Icons.Default.ExitToApp, null) }
        )
    }
}
