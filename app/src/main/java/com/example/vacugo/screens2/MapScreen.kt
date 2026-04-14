package com.example.vacugo.screens2

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MapScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val primaryBlue = Color(0xFF0D47A1)
    val accentBlue = Color(0xFF1976D2)
    val lightGray = Color(0xFFF5F5F5)

    DisposableEffect(Unit) {
        val activity = context as? Activity
        val originalOrientation = activity?.requestedOrientation ?: ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity?.requestedOrientation = originalOrientation
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(32.dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar", tint = Color.Black)
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text("Centros de Vacunación Cercanos", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = primaryBlue)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.SignalCellularAlt, null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.Wifi, null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Icon(Icons.Default.BatteryFull, null, modifier = Modifier.size(16.dp))
        }

        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                    Text("Vacunas\ndisponibles", fontSize = 9.sp, lineHeight = 10.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(Icons.Outlined.FilterList, null, modifier = Modifier.size(14.dp))
                    Icon(Icons.Default.KeyboardArrowDown, null, modifier = Modifier.size(14.dp))
                }
            }

            OutlinedTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier.weight(1f).height(42.dp),
                placeholder = { Text("Buscar dirección o lugar...", fontSize = 12.sp) },
                leadingIcon = { Icon(Icons.Outlined.Search, null, modifier = Modifier.size(18.dp)) },
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = Color.LightGray, unfocusedContainerColor = lightGray),
                singleLine = true
            )

            Button(onClick = {}, shape = RoundedCornerShape(8.dp), colors = ButtonDefaults.buttonColors(containerColor = accentBlue)) {
                Text("Buscar", fontSize = 13.sp)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(shape = RoundedCornerShape(8.dp), border = BorderStroke(1.dp, Color.LightGray), colors = CardDefaults.cardColors(containerColor = Color.White)) {
                    Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FilterAlt, null, modifier = Modifier.size(14.dp))
                        Text(" Filtrar", fontSize = 11.sp)
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
                Switch(checked = true, onCheckedChange = {}, modifier = Modifier.scale(0.7f))
            }
        }

        Row(modifier = Modifier.fillMaxSize()) {
            LazyColumn(modifier = Modifier.weight(0.35f).fillMaxHeight().padding(start = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(sampleCenters) { center -> CenterCard(center) }
            }
            Box(modifier = Modifier.weight(0.65f).fillMaxHeight().padding(8.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFFF0F4F8))) {
                Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Map, null, modifier = Modifier.size(100.dp), tint = Color.LightGray)
                    Text("Cargando Mapa...", color = Color.Gray)
                }
                sampleMarkers.forEach { marker -> MarkerOverlay(marker) }
                Column(modifier = Modifier.align(Alignment.TopEnd).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MapButton(Icons.Default.MyLocation)
                    MapButton(Icons.Default.NearMe)
                }
                Column(modifier = Modifier.align(Alignment.BottomEnd).padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    MapButton(Icons.Default.Add)
                    MapButton(Icons.Default.Remove)
                }
            }
        }
    }
}

@Composable
fun MapButton(icon: androidx.compose.ui.graphics.vector.ImageVector) {
    Surface(modifier = Modifier.size(36.dp), shape = RoundedCornerShape(4.dp), color = Color.White, shadowElevation = 2.dp) {
        Box(contentAlignment = Alignment.Center) { Icon(icon, null, modifier = Modifier.size(20.dp), tint = Color.DarkGray) }
    }
}

@Composable
fun MarkerOverlay(marker: MarkerData) {
    Box(modifier = Modifier.fillMaxSize().padding(start = (marker.x * 400).dp, top = (marker.y * 200).dp)) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            if (marker.showInfo) {
                Card(modifier = Modifier.width(180.dp), shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.LocalHospital, null, tint = marker.color, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(marker.title, fontWeight = FontWeight.Bold, fontSize = 11.sp)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(marker.dist, fontSize = 9.sp, color = Color.Gray)
                        }
                        Text(marker.addr, fontSize = 9.sp, color = Color.Gray)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            repeat(5) { Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(10.dp)) }
                            Text(" 4.5 (210)", fontSize = 8.sp)
                        }
                        Button(onClick = {}, modifier = Modifier.height(20.dp).align(Alignment.End), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)), shape = RoundedCornerShape(2.dp)) {
                            Text("VER DETALLES", fontSize = 7.sp)
                        }
                    }
                }
            }
            Icon(Icons.Default.LocationOn, null, tint = marker.color, modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun CenterCard(center: CenterData) {
    Card(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), border = BorderStroke(1.dp, Color(0xFFEEEEEE))) {
        Row(modifier = Modifier.padding(12.dp)) {
            Icon(Icons.Default.LocalHospital, null, tint = center.color, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(center.name, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("${center.distance} km", color = Color.Gray, fontSize = 12.sp)
                }
                Text(center.address, fontSize = 12.sp, color = Color.Gray)
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
                    repeat(5) { Icon(Icons.Default.Star, null, tint = Color(0xFFFFB300), modifier = Modifier.size(14.dp)) }
                    Text(" ${center.rating} (${center.reviews})", fontSize = 11.sp)
                }
                Button(onClick = {}, modifier = Modifier.height(32.dp).width(120.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32)), shape = RoundedCornerShape(6.dp)) {
                    Text("VER DETALLES", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

data class MarkerData(val title: String, val addr: String, val dist: String, val color: Color, val x: Float, val y: Float, val showInfo: Boolean = false)
val sampleMarkers = listOf(MarkerData("Centro Salud Norte", "Av. Independencia 123", "1,2 km", Color(0xFF2E7D32), 0.1f, 0.2f, true))
data class CenterData(val name: String, val address: String, val distance: String, val rating: String, val reviews: String, val closingTime: String, val color: Color)
val sampleCenters = listOf(CenterData("Centro Salud Norte", "Av. Independencia 123", "1,2", "4.5", "210", "5:00 hoy", Color(0xFF2E7D32)))
