package com.example.vacugo.screens5

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*

@Composable
fun SpaceIn(size: Int) = Spacer(modifier = Modifier.height(size.dp))

@Composable
fun SectionTitleIn(icon: ImageVector, title: String, iconColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(22.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(title, fontWeight = FontWeight.ExtraBold, fontSize = 19.sp, color = iconColor)
    }
}

data class VacunaStock(
    val nombre: String, val centro: String, val cantidad: Int,
    val fechaCaducidad: String, val lotes: List<String>, val estado: String
)

@Composable
fun InventarioScreen(navController: NavController, username: String?) {
    var textoBusqueda by remember { mutableStateOf("") }
    var mostrarTodoInventario by remember { mutableStateOf(false) }
    var verMasAlertas by remember { mutableStateOf(false) }
    val defaultCentro = stringResource(R.string.inv_todos_los_centros)
    var centroSeleccionado by remember { mutableStateOf(defaultCentro) }
    var expandirCentros by remember { mutableStateOf(false) }

    val todasLasVacunas = listOf(
        VacunaStock("Influenza Estacional", "Centro Norte", 120, "2024-11-15", listOf("INF-2023-001"), "Stock Alto"),
        VacunaStock("Pentavalente AcEL", "Hospital Central", 8, "2023-12-20", listOf("PV-LX-992"), "Stock Crítico"),
        VacunaStock("BCG (Tuberculosis)", "Clínica Infantil", 42, "2024-01-05", listOf("BCG-A1"), "Stock Bajo"),
        VacunaStock("Rotavirus", "Hospital Santa Fe", 15, "2024-03-10", listOf("ROT-22"), "Stock Bajo")
    )

    val listaFiltrada = if (mostrarTodoInventario) todasLasVacunas else todasLasVacunas.take(3)

    Scaffold(
        topBar = { InventarioHeader() },
        bottomBar = { BottomMenu(currentRoute = "inventario", navController = navController, username = username) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    SpaceIn(16)

                    Surface(
                        onClick = { expandirCentros = !expandirCentros },
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFF9FAFB),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.LocationOn, null, tint = AzulCielo, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(12.dp))
                            Text(centroSeleccionado, fontWeight = FontWeight.SemiBold, color = Color.DarkGray)
                            Spacer(Modifier.weight(1f))
                            Icon(Icons.Outlined.UnfoldMore, null, tint = Color.Gray)
                        }
                    }

                    if (expandirCentros) {
                        Card(
                            modifier = Modifier.padding(top = 4.dp).fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(6.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            listOf("Hospital General", "Clínica 45", "Centro Norte", defaultCentro).forEach {
                                Text(it, modifier = Modifier.fillMaxWidth().clickable { centroSeleccionado = it; expandirCentros = false }.padding(16.dp))
                            }
                        }
                    }

                    SpaceIn(16)

                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Surface(
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .clickable {
                                    navController.navigate("altaStock/${username ?: "Admin"}")
                                },
                            shape = CircleShape,
                            color = Color.White,
                            shadowElevation = 2.dp,
                            border = BorderStroke(1.dp, AzulCielo.copy(alpha = 0.2f))
                        ) {
                            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.AddCircleOutline, null, tint = AzulCielo, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(stringResource(R.string.inv_btn_entrada), color = AzulCielo, fontWeight = FontWeight.Bold)
                            }
                        }

                        OutlinedButton(
                            onClick = {
                                navController.navigate("centros/${username ?: "Admin"}")
                            },
                            modifier = Modifier.weight(1f).height(48.dp),
                            shape = CircleShape,
                            border = BorderStroke(1.dp, AzulCielo)
                        ) {
                            Icon(Icons.Outlined.SyncAlt, null, tint = AzulCielo)
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.inv_btn_transferir), color = AzulCielo, fontWeight = FontWeight.Bold)
                        }
                    }

                    SpaceIn(24)

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SectionTitleIn(Icons.Outlined.ReportProblem, stringResource(R.string.inv_alertas_prioritarias, 3), Color.Red)
                        IconButton(onClick = { verMasAlertas = !verMasAlertas }) {
                            Icon(if (verMasAlertas) Icons.Outlined.ExpandLess else Icons.Outlined.ChevronRight, null, tint = Color.Gray)
                        }
                    }

                    AlertaItem(stringResource(R.string.inv_alerta_stock_min), stringResource(R.string.inv_alerta_pentavalente), Icons.Outlined.Inventory2, true)
                    SpaceIn(8)
                    AlertaItem(stringResource(R.string.inv_alerta_caducidad), stringResource(R.string.inv_alerta_influenza), Icons.Outlined.WarningAmber, false)

                    if (verMasAlertas) {
                        SpaceIn(8)
                        AlertaItem(stringResource(R.string.inv_alerta_temp), stringResource(R.string.inv_alerta_nevera), Icons.Outlined.Thermostat, true)
                    }

                    SpaceIn(24)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFF3F4F6))
                    ) {
                        TextField(
                            value = textoBusqueda,
                            onValueChange = { textoBusqueda = it },
                            placeholder = { Text(stringResource(R.string.inv_search_placeholder), color = Color.Gray) },
                            leadingIcon = { Icon(Icons.Outlined.Search, null, tint = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent
                            )
                        )
                    }

                    SpaceIn(24)

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Inventory, null, tint = AzulCielo)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.inv_listado_stock), fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                    }
                    SpaceIn(16)
                }
            }

            items(listaFiltrada) { vacuna ->
                Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                    VacunaCardCompleta(vacuna)
                }
                SpaceIn(12)
            }

            item {
                Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    TextButton(onClick = { mostrarTodoInventario = !mostrarTodoInventario }) {
                        Text(if (mostrarTodoInventario) stringResource(R.string.inv_ocultar_extras) else stringResource(R.string.inv_ver_todo), color = AzulCielo, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item {
                SpaceIn(16)
                HorizontalDivider(modifier = Modifier.padding(horizontal = 32.dp), thickness = 1.dp, color = Color(0xFFE5E7EB))
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFFF9FAFB)).padding(16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.BarChart, null, tint = AzulCielo, modifier = Modifier.size(22.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(stringResource(R.string.inv_analisis_titulo), fontWeight = FontWeight.ExtraBold, fontSize = 19.sp, color = Color(0xFF1F2937))
                    }
                    SpaceIn(16)
                    GraficaCard(stringResource(R.string.inv_grafica_distribucion))
                    SpaceIn(12)
                    GraficaCard(stringResource(R.string.inv_grafica_tendencia))
                    SpaceIn(12)
                }
            }
        }
    }
}

@Composable
fun VacunaCardCompleta(vacuna: VacunaStock) {
    val colorDinamico = when (vacuna.estado) {
        "Stock Crítico" -> Color.Red
        "Stock Bajo" -> Color(0xFFEA580C)
        else -> Color(0xFF16A34A)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(vacuna.nombre, fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.LocationOn, null, tint = Color.Gray, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(vacuna.centro, color = Color.Gray, fontSize = 12.sp)
                    }
                }
                Box(Modifier.background(colorDinamico.copy(alpha = 0.1f), RoundedCornerShape(8.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                    Text(vacuna.estado, color = colorDinamico, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                }
            }
            SpaceIn(16)
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.Bottom) {
                Column(Modifier.weight(0.4f)) {
                    Text(stringResource(R.string.inv_label_cantidad), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Text(stringResource(R.string.inv_unidades, vacuna.cantidad), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = colorDinamico)
                }
                Column(Modifier.weight(0.6f), horizontalAlignment = Alignment.End) {
                    Text(stringResource(R.string.inv_label_caducidad), fontSize = 10.sp, color = Color.Gray, fontWeight = FontWeight.Bold)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.CalendarToday, null, tint = AzulCielo, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(vacuna.fechaCaducidad, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            SpaceIn(12)
            Row(verticalAlignment = Alignment.CenterVertically) {
                vacuna.lotes.forEach { lote ->
                    Surface(color = Color(0xFFF1F5F9), shape = RoundedCornerShape(4.dp)) {
                        Text(stringResource(R.string.inv_label_lote, lote), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Spacer(Modifier.weight(1f))
                Icon(Icons.Outlined.Edit, null, tint = Color.LightGray, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(12.dp))
                Icon(Icons.Outlined.FileDownload, null, tint = AzulCielo, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(12.dp))
                Icon(Icons.Outlined.MoreVert, null, tint = Color.Gray, modifier = Modifier.size(18.dp))
            }
        }
    }
}

@Composable
fun AlertaItem(titulo: String, subtitulo: String, icon: ImageVector, isCritical: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isCritical) Color(0xFFFEF2F2) else Color(0xFFF9FAFB), RoundedCornerShape(12.dp))
            .border(1.dp, if (isCritical) Color(0xFFFECACA) else Color(0xFFE5E7EB), RoundedCornerShape(12.dp))
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(32.dp).background(Color.White, CircleShape), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = if (isCritical) Color.Red else Color.DarkGray, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(12.dp))
        Column {
            Text(titulo, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(subtitulo, fontSize = 12.sp, color = Color.Gray)
        }
    }
}

@Composable
fun GraficaCard(titulo: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(titulo, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
            Box(Modifier.fillMaxWidth().height(110.dp), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.inv_grafica_preview), color = Color.LightGray, fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun InventarioHeader() {
    Surface(color = Color.White, shadowElevation = 1.dp) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 12.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(Modifier.size(32.dp).background(azulito, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Shield, null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Text(stringResource(R.string.inv_header_title), fontSize = 17.sp, fontWeight = FontWeight.ExtraBold)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Outlined.Notifications, null, tint = Color.Gray)
        }
    }
}