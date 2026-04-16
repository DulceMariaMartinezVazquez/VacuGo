package com.example.vacugo.screens4

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*


@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // --- HEADER ---
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Vaccines, null, tint = BrandBlue, modifier = Modifier.size(30.dp))
                Text(stringResource(R.string.app_name), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp, modifier = Modifier.padding(start = 8.dp))
            }
            Icon(Icons.Outlined.Home, null, tint = BrandBlue)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(BgGray)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = StatusBadgeBg)
            ) {
                Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box {
                        Surface(modifier = Modifier.size(60.dp), shape = CircleShape, color = Color(0xFFE0E0E0)) {
                            Icon(Icons.Default.Person, null, tint = Color.Gray, modifier = Modifier.padding(12.dp))
                        }
                        Box(modifier = Modifier.size(14.dp).align(Alignment.BottomEnd).background(Color.White, CircleShape).padding(2.dp)) {
                            Box(modifier = Modifier.fillMaxSize().background(OnlineGreen, CircleShape))
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(stringResource(R.string.doctor_nombre7), fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
                        Text(stringResource(R.string.doctor_info), color = Color.Gray, fontSize = 13.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(color = Color.White, shape = RoundedCornerShape(12.dp)) {
                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                                    Icon(Icons.Default.PlayCircleOutline, null, modifier = Modifier.size(14.dp))
                                    Text(stringResource(R.string.en_servicio), fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(stringResource(R.string.cambiar), fontSize = 11.sp, modifier = Modifier.border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)).padding(horizontal = 10.dp, vertical = 4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                KpiCard(stringResource(R.string.pacientes), "28", Icons.Outlined.People, KpiBlueBg, BrandBlue, modifier = Modifier.weight(1f))
                KpiCard(stringResource(R.string.tiempo_prom), "14m", Icons.Outlined.Timer, KpiGreenBg, KpiGreenText, modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.border(2.dp, SkyBlueOutline, CircleShape).padding(4.dp)) {
                    Icon(Icons.Default.PlayArrow, null, tint = BrandBlue, modifier = Modifier.size(14.dp))
                }
                Text(stringResource(R.string.fila_virtual), fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, modifier = Modifier.padding(start = 8.dp))
                Spacer(modifier = Modifier.weight(1f))
                Surface(color = StatusBadgeBg, shape = RoundedCornerShape(12.dp)) {
                    Text(stringResource(R.string.pendientes), fontSize = 11.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp))
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = 0.65f,
                modifier = Modifier.fillMaxWidth().height(6.dp).clip(CircleShape),
                color = BrandBlue,
                trackColor = Color(0xFFD6D9DC)
            )
            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = BorderStroke(1.dp, Color(0xFFE0E0E0))
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Surface(color = KpiBlueBg, shape = RoundedCornerShape(14.dp), modifier = Modifier.size(65.dp).border(2.dp, BrandBlue.copy(alpha = 0.5f), RoundedCornerShape(14.dp))) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                                Text(stringResource(R.string.turno), fontSize = 9.sp, color = BrandBlue, fontWeight = FontWeight.Bold)
                                Text("#14", fontSize = 20.sp, color = BrandBlue, fontWeight = FontWeight.ExtraBold)
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(stringResource(R.string.paciente_nombre), fontWeight = FontWeight.ExtraBold, fontSize = 17.sp)
                            Text(stringResource(R.string.motivo), color = Color.Gray, fontSize = 13.sp)
                            Text(stringResource(R.string.en_consultorio), fontWeight = FontWeight.ExtraBold, fontSize = 11.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Button(onClick = {}, modifier = Modifier.weight(1f).height(50.dp), colors = ButtonDefaults.buttonColors(containerColor = BrandBlue), shape = RoundedCornerShape(14.dp)) {
                            Text(stringResource(R.string.finalizar_turno), fontWeight = FontWeight.ExtraBold)
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        OutlinedButton(onClick = {}, modifier = Modifier.size(50.dp), shape = RoundedCornerShape(14.dp), contentPadding = PaddingValues(0.dp)) {
                            Icon(Icons.Default.MoreVert, null, tint = Color.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Timeline, null, tint = Color(0xFF00C48C), modifier = Modifier.size(18.dp))
                                Text(stringResource(R.string.rendimiento_semanal), fontWeight = FontWeight.ExtraBold, fontSize = 16.sp, modifier = Modifier.padding(start = 4.dp))
                            }
                            Text(stringResource(R.string.dosis_dia), color = Color.Gray, fontSize = 12.sp)
                        }
                        Surface(color = Color(0xFFE4F9F2), shape = RoundedCornerShape(8.dp)) {
                            Text("+12.4%", color = Color(0xFF0C8462), fontSize = 12.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
                        }
                    }
                    Box(modifier = Modifier.fillMaxWidth().height(140.dp).padding(vertical = 16.dp), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.espacio_grafica), color = Color.LightGray)
                    }
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        listOf("Mar", "Mie", "Jue", "Vie", "Sab", "Dom").forEach { Text(it, fontSize = 11.sp, color = Color.Gray) }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Edit, null, tint = BrandBlue, modifier = Modifier.size(20.dp))
                Text(stringResource(R.string.stock_critico), fontWeight = FontWeight.ExtraBold, fontSize = 17.sp, modifier = Modifier.padding(start = 6.dp))
                Spacer(modifier = Modifier.weight(1f))
                Text(stringResource(R.string.ver_todo), color = BrandBlue, fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Column(modifier = Modifier.padding(top = 16.dp).background(Color.White, RoundedCornerShape(16.dp))) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(StatusBadgeBg, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(stringResource(R.string.insumo), modifier = Modifier.weight(2f), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold)
                    Text(stringResource(R.string.stock), modifier = Modifier.weight(1f), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
                    Text(stringResource(R.string.estado), modifier = Modifier.weight(1.2f), fontSize = 11.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.End)
                }
                StockRow(stringResource(R.string.antigripal), "14", stringResource(R.string.alerta), Color.Red)
                StockRow(stringResource(R.string.hepatitis), "45", stringResource(R.string.ok), KpiGreenText)
                StockRow(stringResource(R.string.doble_adulto), "8", stringResource(R.string.alerta), Color.Red)
            }

            Spacer(modifier = Modifier.height(28.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                DashedAction(stringResource(R.string.historial7), Icons.Default.Search, Modifier.weight(1f))
                DashedAction(stringResource(R.string.nueva_nota), Icons.Default.AddCircleOutline, Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Composable
fun StockRow(name: String, qty: String, status: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(name, modifier = Modifier.weight(2f), fontSize = 14.sp, fontWeight = FontWeight.Medium)

        Text(
            text = qty,
            modifier = Modifier.weight(1f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Box(modifier = Modifier.weight(1.2f), contentAlignment = Alignment.CenterEnd) {
            Surface(color = color.copy(alpha = 0.1f), shape = RoundedCornerShape(8.dp)) {
                Text(status, color = color, fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp))
            }
        }
    }
}

@Composable
fun KpiCard(title: String, value: String, icon: androidx.compose.ui.graphics.vector.ImageVector, bgColor: Color, iconColor: Color, modifier: Modifier) {
    Card(modifier = modifier.height(110.dp), colors = CardDefaults.cardColors(containerColor = bgColor), shape = RoundedCornerShape(24.dp)) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween) {
            Surface(modifier = Modifier.size(34.dp), shape = RoundedCornerShape(10.dp), color = Color.White.copy(alpha = 0.6f)) {
                Icon(icon, null, tint = iconColor, modifier = Modifier.padding(7.dp))
            }
            Column {
                Text(value, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)
                Text(title, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, color = Color.DarkGray)
            }
        }
    }
}

@Composable
fun DashedAction(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier) {
    Box(
        modifier = modifier.height(90.dp).drawBehind {
            drawRoundRect(color = Color.LightGray, style = Stroke(width = 2f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(24.dp.toPx()))
        }.padding(12.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = BrandBlue, modifier = Modifier.size(26.dp))
            Text(title, fontSize = 10.sp, fontWeight = FontWeight.ExtraBold, textAlign = TextAlign.Center)
        }
    }
}