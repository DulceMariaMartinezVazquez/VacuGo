package com.example.vacugo.screens3

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*

data class CentroVacunacion(
    val id: Int,
    val nombre: String,
    val direccion: String,
    val distancia: Double,
    val rating: Double,
    val totalResenas: Int,
    val horarioCierre: String,
    val estaAbierto: Boolean,
    val pinColor: Color
)

val centrosEjemplo = listOf(
    CentroVacunacion(1, "Centro Salud Norte",  "Av. Independencia 123", 1.2, 4.5, 210, "5:00",    true, Color(0xFF2E7D32)),
    CentroVacunacion(2, "Hospital Central",    "Calle Real 456",        1.8, 4.6, 178, "6:00",    true, Color(0xFFE53935)),
    CentroVacunacion(3, "Clínica Sur",         "Boulevard Sur 789",     2.3, 4.4, 153, "4:00",    true, Color(0xFFF9A825)),
    CentroVacunacion(4, "Centro Médico Este",  "Calle Jardín 234",      2.9, 4.5, 125, "7:00 PM", true, Color(0xFF1565C0)),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CentrosScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current

    // ── Forzar orientación horizontal ────────────────────────────────────────
    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    var busqueda           by remember { mutableStateOf("") }
    var centroSeleccionado by remember { mutableStateOf<CentroVacunacion?>(centrosEjemplo.first()) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // ── Top bar ───────────────────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .statusBarsPadding()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.centros_back_desc),
                    tint = Primary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Text(
                stringResource(R.string.centros_titulo),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Primary,
                modifier = Modifier.weight(1f)
            )
        }

        // ── Search + filter bar ───────────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Vacunas disponibles chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFBBBBBB), RoundedCornerShape(8.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Text(
                    stringResource(R.string.centros_chip_vacunas),
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }

            // Filtrar chip
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color(0xFFBBBBBB), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.FilterList,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = TextSecondary
                    )
                    Text(stringResource(R.string.centros_filtrar), fontSize = 12.sp, color = TextSecondary)
                }
            }

            // Campo de búsqueda
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = {
                    Text(stringResource(R.string.centros_buscar_placeholder), fontSize = 12.sp)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null, modifier = Modifier.size(16.dp))
                },
                modifier = Modifier
                    .weight(1f)
                    .height(46.dp),
                shape = RoundedCornerShape(8.dp),
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 12.sp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Color(0xFFBBBBBB)
                )
            )

            // Botón Buscar
            Button(
                onClick = {},
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                modifier = Modifier.height(46.dp)
            ) {
                Text(stringResource(R.string.centros_btn_buscar), fontSize = 12.sp)
            }

            // Toggle Filtrar (derecha)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    Icons.Default.FilterList,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = TextSecondary
                )
                Text(stringResource(R.string.centros_filtrar), fontSize = 11.sp, color = TextSecondary)
                Switch(
                    checked = true,
                    onCheckedChange = {},
                    modifier = Modifier.graphicsLayer(scaleX = 0.7f, scaleY = 0.7f),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Primary
                    )
                )
            }
        }

        HorizontalDivider(color = Color(0xFFEEEEEE))

        // ── Cuerpo principal: Lista | Mapa ────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
        ) {

            // ── Lista de centros ──────────────────────────────────────────────
            LazyColumn(
                modifier = Modifier
                    .width(290.dp)
                    .fillMaxHeight()
                    .background(Color.White),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(centrosEjemplo) { centro ->
                    CentroListItem(
                        centro = centro,
                        isSelected = centroSeleccionado?.id == centro.id,
                        onClick = { centroSeleccionado = centro }
                    )
                    HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 0.5.dp)
                }
            }

            // Divisor vertical
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .background(Color(0xFFDDDDDD))
            )

            // ── Mapa simulado ─────────────────────────────────────────────────
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .background(Color(0xFFE8ECD8))
            ) {
                MapaSimulado()

                // Pins sobre el mapa
                centrosEjemplo.forEach { centro ->
                    val (offsetX, offsetY) = when (centro.id) {
                        1 -> Pair(110.dp,  55.dp)
                        2 -> Pair(220.dp,  80.dp)
                        3 -> Pair(70.dp,  170.dp)
                        4 -> Pair(270.dp, 195.dp)
                        else -> Pair(100.dp, 100.dp)
                    }
                    Box(modifier = Modifier.offset(x = offsetX, y = offsetY)) {
                        PinMarca(
                            centro = centro,
                            isSelected = centroSeleccionado?.id == centro.id
                        )
                    }
                }

                // Tooltip del centro seleccionado
                centroSeleccionado?.let { centro ->
                    val (offsetX, offsetY) = when (centro.id) {
                        1 -> Pair(120.dp,  10.dp)
                        2 -> Pair(180.dp,  40.dp)
                        3 -> Pair(30.dp,  130.dp)
                        4 -> Pair(160.dp, 155.dp)
                        else -> Pair(100.dp, 50.dp)
                    }
                    Box(modifier = Modifier.offset(x = offsetX, y = offsetY)) {
                        TooltipMapa(centro = centro)
                    }
                }

                // Escala del mapa
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Box(Modifier.width(40.dp).height(2.dp).background(Color(0xFF555555)))
                    Text("200 m", fontSize = 10.sp, color = Color(0xFF555555))
                }

                // Botones zoom
                Column(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.White)
                        .border(0.5.dp, Color(0xFFCCCCCC), RoundedCornerShape(4.dp))
                ) {
                    Text(
                        "+",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontSize = 18.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                    HorizontalDivider(color = Color(0xFFCCCCCC))
                    Text(
                        "−",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        fontSize = 18.sp,
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// ── Item de lista ─────────────────────────────────────────────────────────────
@Composable
private fun CentroListItem(
    centro: CentroVacunacion,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isSelected) Color(0xFFF0F4FF) else Color.White)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Pin color indicator
        Box(
            modifier = Modifier
                .size(30.dp)
                .clip(RoundedCornerShape(50))
                .background(centro.pinColor.copy(alpha = 0.15f))
                .border(1.5.dp, centro.pinColor, RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Text("+", fontSize = 16.sp, color = centro.pinColor, fontWeight = FontWeight.Bold)
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    centro.nombre,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${centro.distancia} km",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextSecondary
                )
            }
            Text(centro.direccion, fontSize = 11.sp, color = TextSecondary)

            // Estrellas
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                val fullStars = centro.rating.toInt()
                repeat(5) { i ->
                    Icon(
                        imageVector = if (i < fullStars) Icons.Default.Star else Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(13.dp)
                    )
                }
                Text(
                    " ${centro.rating} (${centro.totalResenas})",
                    fontSize = 10.sp,
                    color = TextSecondary
                )
            }

            Text(
                stringResource(R.string.centros_abierto_hasta, centro.horarioCierre),
                fontSize = 11.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Medium
            )

            Button(
                onClick = {},
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1B5E20)),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                modifier = Modifier
                    .padding(top = 4.dp)
                    .height(30.dp)
            ) {
                Text(
                    stringResource(R.string.centros_btn_ver_detalles),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}

// ── Pin del mapa ──────────────────────────────────────────────────────────────
@Composable
private fun PinMarca(centro: CentroVacunacion, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(if (isSelected) 38.dp else 30.dp)
            .clip(RoundedCornerShape(50))
            .background(centro.pinColor)
            .border(2.dp, Color.White, RoundedCornerShape(50)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            "+",
            fontSize = if (isSelected) 20.sp else 15.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}

// ── Tooltip del mapa ──────────────────────────────────────────────────────────
@Composable
private fun TooltipMapa(centro: CentroVacunacion) {
    Card(
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier.width(190.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    centro.nombre,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${centro.distancia} km",
                    fontSize = 11.sp,
                    color = TextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(centro.direccion, fontSize = 10.sp, color = TextSecondary)

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                repeat(centro.rating.toInt()) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = Color(0xFFFFC107),
                        modifier = Modifier.size(10.dp)
                    )
                }
                Text(
                    " ${centro.rating} (${centro.totalResenas})",
                    fontSize = 9.sp,
                    color = TextSecondary
                )
            }

            Text(
                stringResource(R.string.centros_abierto_hasta, centro.horarioCierre),
                fontSize = 10.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Medium
            )

            Button(
                onClick = {},
                shape = RoundedCornerShape(6.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(28.dp)
            ) {
                Text(
                    stringResource(R.string.centros_btn_ver_detalles),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.3.sp
                )
            }
        }
    }
}

// ── Mapa simulado ─────────────────────────────────────────────────────────────
@Composable
private fun MapaSimulado() {
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFE8ECD8))) {
        // Calles horizontales
        listOf(80.dp, 160.dp, 240.dp, 320.dp).forEach { y ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .offset(y = y)
                    .background(Color(0xFFFFFDE7))
            )
        }
        // Calles verticales
        listOf(100.dp, 220.dp, 340.dp).forEach { x ->
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .offset(x = x)
                    .background(Color(0xFFFFFDE7))
            )
        }
        // Bloques de manzanas
        listOf(
            Triple(10.dp,  10.dp, Pair(80.dp, 60.dp)),
            Triple(118.dp, 10.dp, Pair(90.dp, 60.dp)),
            Triple(228.dp, 10.dp, Pair(100.dp, 60.dp)),
            Triple(10.dp,  90.dp, Pair(80.dp, 60.dp)),
            Triple(118.dp, 90.dp, Pair(90.dp, 60.dp)),
            Triple(228.dp, 90.dp, Pair(100.dp, 60.dp)),
        ).forEach { (x, y, size) ->
            Box(
                modifier = Modifier
                    .offset(x = x, y = y)
                    .width(size.first)
                    .height(size.second)
                    .background(Color(0xFFD4E0C0))
            )
        }
        // Parque
        Box(
            modifier = Modifier
                .offset(x = 10.dp, y = 10.dp)
                .width(70.dp)
                .height(55.dp)
                .background(Color(0xFFA5C87A))
        ) {
            Text(
                stringResource(R.string.centros_mapa_parque),
                fontSize = 9.sp,
                color = Color(0xFF4A7C2E),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
        // Plaza
        Box(
            modifier = Modifier
                .offset(x = 260.dp, y = 230.dp)
                .width(60.dp)
                .height(50.dp)
                .background(Color(0xFFA5C87A))
        ) {
            Text(
                stringResource(R.string.centros_mapa_plaza),
                fontSize = 9.sp,
                color = Color(0xFF4A7C2E),
                modifier = Modifier.align(Alignment.Center),
                textAlign = TextAlign.Center
            )
        }
        // Punto azul "mi ubicación"
        Box(
            modifier = Modifier
                .offset(x = 135.dp, y = 148.dp)
                .size(14.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White)
                .border(3.dp, Color(0xFF1565C0), RoundedCornerShape(50))
        )
    }
}