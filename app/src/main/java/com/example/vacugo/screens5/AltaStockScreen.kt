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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.vacugo.ui.theme.*
import androidx.compose.ui.res.stringResource
import com.example.vacugo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AltaStockScreen(navController: NavController, username: String?) {
    // 1. ESTADOS PARA LOS INPUTS
    var vacunaNombre by remember { mutableStateOf("") }
    var lote by remember { mutableStateOf("") }
    var unidades by remember { mutableStateOf("0") }
    var fecha by remember { mutableStateOf("13/05/2024") }

    val coloresPersonalizados = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = AzulCielo,
        unfocusedBorderColor = Color(0xFFE5E7EB),
        errorBorderColor = Color(0xFFFCA5A5),
        focusedContainerColor = Color(0xFFF9FAFB),
        unfocusedContainerColor = Color(0xFFF9FAFB),
        errorContainerColor = Color(0xFFFFF5F5),
        focusedTextColor = Color(0xFF1F2937),
        unfocusedTextColor = Color(0xFF4B5563)
    )

    Scaffold(
        bottomBar = {
            BottomMenu(currentRoute = "alta_stock", navController = navController, username = username)
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .padding(horizontal = 20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Outlined.ArrowBack,
                            contentDescription = stringResource(id = R.string.regresar)
                        )
                    }
                    IconButton(onClick = { /* Acción Historial */ }) {
                        Icon(
                            Icons.Outlined.History,
                            contentDescription = stringResource(id = R.string.historial)
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Vaccines,
                        contentDescription = null,
                        tint = AzulCielo,
                        modifier = Modifier.size(35.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.vacugo),
                        color = AzulCielo,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = R.string.alta_stock),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black
                )

                Text(
                    text = stringResource(id = R.string.descripcion_stock),
                    color = Color.Gray,
                    fontSize = 14.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
                    color = Color.White
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.size(36.dp).background(Color(0xFFF0FDF4), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Outlined.CheckCircle, null, tint = Color(0xFF22C55E), modifier = Modifier.size(20.dp))
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = stringResource(id = R.string.entrada_confirmada),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )

                            Text(
                                text = stringResource(id = R.string.stock_actualizado),
                                fontSize = 12.sp,
                                color = Color.Gray
                            )
                        }
                        Icon(Icons.Outlined.Close, null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // CAMPO: Nombre de la vacuna
                Text(
                    text = stringResource(id = R.string.nombre_vacuna),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                OutlinedTextField(
                    value = vacunaNombre,
                    onValueChange = { vacunaNombre = it },
                    placeholder = { Text(stringResource(id = R.string.placeholder_vacuna)) },
                    leadingIcon = { Icon(Icons.Outlined.Search, null, tint = Color.Gray) },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    isError = vacunaNombre.isEmpty(),
                    colors = coloresPersonalizados
                )

                if (vacunaNombre.isEmpty()) {
                    Text(
                        text = stringResource(id = R.string.campo_obligatorio),
                        color = Color(0xFFF87171),
                        fontSize = 11.sp,
                        modifier = Modifier.padding(top = 4.dp, start = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(id = R.string.lote),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )

                OutlinedTextField(
                    value = lote,
                    onValueChange = { lote = it },
                    placeholder = { Text(stringResource(id = R.string.placeholder_lote)) },
                    leadingIcon = { Icon(Icons.Outlined.Segment, null, tint = Color.Gray) },
                    trailingIcon = { Icon(Icons.Default.Add, null, tint = AzulCielo) },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = coloresPersonalizados
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.unidades),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        OutlinedTextField(
                            value = unidades,
                            onValueChange = { unidades = it },
                            leadingIcon = { Icon(Icons.Outlined.Inventory2, null, tint = Color.Gray) },
                            modifier = Modifier.padding(top = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            isError = unidades == "0",
                            colors = coloresPersonalizados
                        )
                        if (unidades == "0") {
                            Text(
                                text = stringResource(id = R.string.unidades_error),
                                color = Color(0xFFF87171),
                                fontSize = 11.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = stringResource(id = R.string.fecha_entrada),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        OutlinedTextField(
                            value = fecha,
                            onValueChange = { fecha = it },
                            leadingIcon = { Icon(Icons.Outlined.CalendarToday, null, tint = Color.Gray) },
                            modifier = Modifier.padding(top = 8.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = coloresPersonalizados
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { /* Guardar Stock */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = AzulCielo)
                ) {
                    Icon(Icons.Default.Add, null)
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.agregar_entrada),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                FilledTonalButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(containerColor = Color(0xFFF3F4F6))
                ) {
                    Text(
                        text = stringResource(id = R.string.cancelar),
                        color = Color(0xFF1F2937),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(8.dp).background(AzulCielo, CircleShape))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = stringResource(id = R.string.entradas_recientes),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.weight(1f))
                    Surface(color = Color(0xFFEFF6FF), shape = RoundedCornerShape(12.dp)) {
                        Text(
                            text = stringResource(id = R.string.ultimas_5),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            color = AzulCielo,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            val listaEntradas = listOf(
                EntradaRecienteData("Pfizer-BioNTech COVID-19", "BNT162B2-984", "12 May, 10:30", "+120"),
                EntradaRecienteData("Moderna Spikevax", "MOD-7721-X", "11 May, 15:45", "+50"),
                EntradaRecienteData("Gripe Estacional Vaxigrip", "VX-2024-01", "11 May, 09:15", "+200")
            )

            items(listaEntradas) { entrada ->
                EntradaRecienteCard(entrada)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Box(Modifier.fillMaxWidth().padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(id = R.string.ver_historial),
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}

data class EntradaRecienteData(val nombre: String, val lote: String, val fecha: String, val cantidad: String)

@Composable
fun EntradaRecienteCard(entrada: EntradaRecienteData) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(40.dp).background(Color(0xFFEFF6FF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Inventory2, null, tint = AzulCielo, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(entrada.nombre, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Segment, null, tint = Color.LightGray, modifier = Modifier.size(12.dp))
                    Text(" ${entrada.lote}  •  ${entrada.fecha}", color = Color.Gray, fontSize = 11.sp)
                }
            }
            Surface(color = Color(0xFFEFF6FF), shape = RoundedCornerShape(12.dp)) {
                Text(entrada.cantidad, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = AzulCielo, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

