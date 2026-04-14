package com.example.vacugo.screens5

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*

@Composable
fun Space(size: Int) = Spacer(modifier = Modifier.height(size.dp))

@Composable
fun HomeAdminScreen(
    navController: NavController,
    username: String?
) {
    Scaffold(
        topBar = { Header() },
        bottomBar = {
            BottomMenu(
                currentRoute = "home",
                navController = navController,
                username = username
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Fondo)
                .padding(horizontal = 16.dp)
        ) {
            item { Space(12) }

            item {
                SectionTitle(
                    icon = Icons.Outlined.ShowChart,
                    title = stringResource(id = R.string.metricas_principales),
                    iconColor = AzulCielo
                )
            }

            item { Space(16) }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MiniCard(
                        stringResource(id = R.string.usuarios),
                        "42,850",
                        AzulClaro,
                        AzulCielo,
                        Icons.Outlined.Group,
                        Modifier.weight(1f)
                    )

                    MiniCard(
                        stringResource(id = R.string.hoy),
                        "1,420",
                        VerdeClaro,
                        Color(0xFF16A34A),
                        Icons.Outlined.MedicalServices,
                        Modifier.weight(1f)
                    )
                }
            }

            item { Space(12) }

            item {
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MiniCard(
                        stringResource(id = R.string.este_mes),
                        "28,300",
                        NaranjaClaro,
                        Color(0xFFEA580C),
                        Icons.Outlined.DateRange,
                        Modifier.weight(1f)
                    )

                    MiniCard(
                        stringResource(id = R.string.personal),
                        "842",
                        MoradoClaro,
                        Color(0xFF9333EA),
                        Icons.Outlined.Person,
                        Modifier.weight(1f)
                    )
                }
            }

            item { Space(20) }
            item { CentrosOperativosCard() }
            item { Space(20) }

            item {
                SectionTitle(
                    icon = Icons.Outlined.Warning,
                    title = stringResource(id = R.string.alertas_sistema),
                    iconColor = Color.Red
                )
            }

            item { Space(12) }
            item { AlertasSistemaCard() }
            item { Space(20) }

            item {
                SectionTitle(
                    icon = Icons.Outlined.BarChart,
                    title = stringResource(id = R.string.graficas_desempeno),
                    iconColor = AzulCielo
                )
            }

            item { Space(8) }

            item {
                Text(
                    text = stringResource(id = R.string.vacunacion_diaria),
                    fontWeight = FontWeight.Medium
                )
            }

            item { Space(12) }
            item { GraficaCard() }
            item { Space(16) }
        }
    }
}

@Composable
fun Header() {
    Surface(color = Color.White, shadowElevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 14.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(AzulCielo, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Shield, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(id = R.string.dashboard_admin),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                Icons.Outlined.Home,
                contentDescription = null,
                tint = AzulMorado
            )
        }
    }
}

@Composable
fun SectionTitle(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    iconColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = iconColor)
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
    }
}

@Composable
fun MiniCard(
    title: String,
    value: String,
    backgroundColor: Color,
    iconColor: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(backgroundColor, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor)
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(title, fontSize = 14.sp, color = Color.Gray)

            Spacer(modifier = Modifier.height(4.dp))

            Text(value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun GraficaCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(stringResource(id = R.string.aqui_grafica))
        }
    }
}

@Composable
fun BottomMenu(
    currentRoute: String,
    navController: NavController,
    username: String?
) {

    val user = username ?: "Admin"

    val items = listOf(
        Triple("home", stringResource(id = R.string.menu_inicio), Icons.Outlined.Home),
        Triple("personal", stringResource(id = R.string.menu_personal), Icons.Outlined.Badge),
        Triple("usuarios", stringResource(id = R.string.menu_usuarios), Icons.Outlined.PersonOutline),
        Triple("inventario", stringResource(id = R.string.menu_inventario), Icons.Outlined.Inventory),
        Triple("ajustes", stringResource(id = R.string.menu_ajustes), Icons.Outlined.Settings)
    )

    Surface(
        color = Color.White,
        shadowElevation = 8.dp
    ) {

        NavigationBar(
            containerColor = Color.White
        ) {

            items.forEach { (route, label, icon) ->

                NavigationBarItem(
                    selected = currentRoute == route,
                    onClick = {

                        val destino = when (route) {
                            "home" -> "home"
                            "usuarios" -> "usuarios/$user"
                            "personal" -> "personal/$user"
                            "inventario" -> "inventario/$user"
                            "ajustes" -> "centros/$user"
                            else -> "home"
                        }

                        navController.navigate(destino) {
                            popUpTo("home")
                            launchSingleTop = true
                        }
                    },
                    icon = { Icon(icon, null) },
                    label = { Text(label, fontSize = 10.sp) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = AzulCielo,
                        selectedTextColor = AzulCielo,
                        unselectedIconColor = Color.Gray,
                        unselectedTextColor = Color.Gray,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }
    }
}

@Composable
fun CentrosOperativosCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(AzulClaro, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.LocationOn,
                    contentDescription = null,
                    tint = AzulFuerte,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(id = R.string.centros_operativos),
                fontSize = 14.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = stringResource(id = R.string.total_centros),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AlertasSistemaCard() {

    var mostrarCaducar by remember { mutableStateOf(false) }
    var mostrarErrores by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Outlined.Inventory,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    stringResource(id = R.string.inventario_critico),
                    fontWeight = FontWeight.Bold
                )
            }

            Space(12)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(id = R.string.hospital_general),
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .background(Color.Red, RoundedCornerShape(10.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        stringResource(id = R.string.dosis_120),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Space(16)

            ExpandableRow(
                title = stringResource(id = R.string.proximos_caducar),
                icon = Icons.Outlined.Schedule,
                expanded = mostrarCaducar,
                onToggle = { mostrarCaducar = !mostrarCaducar }
            )

            if (mostrarCaducar) {
                Space(8)
                Text(stringResource(id = R.string.sin_registros))
            }

            Space(16)

            Divider(thickness = 1.dp, color = Color(0xFFE5E7EB))

            Space(16)

            ExpandableRow(
                title = stringResource(id = R.string.errores_sistema),
                icon = Icons.Outlined.Error,
                expanded = mostrarErrores,
                onToggle = { mostrarErrores = !mostrarErrores }
            )

            if (mostrarErrores) {
                Space(8)
                Text(stringResource(id = R.string.sin_registros))
            }

            Space(16)

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                OutlinedButton(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(0.8f),
                    border = BorderStroke(1.dp, Color.Red),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.White,
                        contentColor = Color.Red
                    )
                ) {
                    Text(stringResource(id = R.string.gestionar_productos))
                }
            }
        }
    }
}

@Composable
fun ExpandableRow(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    expanded: Boolean,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggle() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = if (expanded)
                Icons.Outlined.ExpandMore
            else
                Icons.Outlined.ChevronRight,
            contentDescription = null
        )
    }
}