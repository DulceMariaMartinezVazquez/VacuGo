package com.example.vacugo.screens3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*

private enum class Esquema { COMPLETO, INCOMPLETO, PENDIENTE }

private data class Vacuna(
    val nombre: String,
    val fecha: String,
    val dosis: String,
    val centro: String,
    val esquema: Esquema
)

private val listaVacunas = listOf(
    Vacuna("COVID-19 Pfizer",      "15 Sep 2023", "Refuerzo",    "Centro de Salud Norte",  Esquema.COMPLETO),
    Vacuna("Influenza 2023",       "01 Oct 2023", "Dosis Única", "Clínica del Valle",       Esquema.COMPLETO),
    Vacuna("Hepatitis B",          "10 Mar 2023", "2da Dosis",   "Hospital General",        Esquema.INCOMPLETO),
    Vacuna("Tétanos",              "22 Jun 2022", "Refuerzo",    "Centro Médico Sur",       Esquema.COMPLETO),
    Vacuna("Sarampión (MMR)",      "05 Ene 2022", "1ra Dosis",   "Clínica Familiar",        Esquema.PENDIENTE),
    Vacuna("COVID-19 AstraZeneca", "10 Ago 2021", "2da Dosis",   "Módulo Vacunación Plaza", Esquema.COMPLETO),
)

@Composable
fun HistorialScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    var busqueda     by remember { mutableStateOf("") }
    var filtroActivo by remember { mutableStateOf<String?>(null) }

    val filtroCovid    = stringResource(R.string.historial_filtro_covid)
    val filtroUltimoAnio = stringResource(R.string.historial_filtro_ultimo_anio)
    val filtroInfluenza  = stringResource(R.string.historial_filtro_influenza)
    val filtros = listOf(filtroCovid, filtroUltimoAnio, filtroInfluenza)

    val vacunasFiltradas = listaVacunas.filter { vacuna ->
        val coincideBusqueda = busqueda.isEmpty() ||
                vacuna.nombre.contains(busqueda, ignoreCase = true) ||
                vacuna.centro.contains(busqueda, ignoreCase = true)
        val coincideFiltro = when (filtroActivo) {
            filtroCovid      -> vacuna.nombre.contains("COVID", ignoreCase = true)
            filtroUltimoAnio -> true
            filtroInfluenza  -> vacuna.nombre.contains("Influenza", ignoreCase = true)
            else             -> true
        }
        coincideBusqueda && coincideFiltro
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Toolbar
        Surface(color = Surface, shadowElevation = 2.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.historial_back_desc),
                        tint = Primary
                    )
                }
                Spacer(Modifier.width(4.dp))
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Primary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.HealthAndSafety,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Spacer(Modifier.width(8.dp))
                Text(
                    stringResource(R.string.historial_titulo),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
            }
        }


        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {

            // Estadísticas
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    EstadisticaItem(
                        icono = Icons.Default.HealthAndSafety,
                        iconColor = Primary,
                        iconBg = PrimaryLight,
                        etiqueta = stringResource(R.string.historial_stat_total),
                        valor = "${listaVacunas.size}"
                    )
                    VerticalDivider(modifier = Modifier.height(56.dp), color = Divider)
                    EstadisticaItem(
                        icono = Icons.Default.History,
                        iconColor = Primary,
                        iconBg = PrimaryLight,
                        etiqueta = stringResource(R.string.historial_stat_ultima),
                        valor = stringResource(R.string.historial_stat_ultima_valor)
                    )
                    VerticalDivider(modifier = Modifier.height(56.dp), color = Divider)
                    EstadisticaItem(
                        icono = Icons.Default.CalendarMonth,
                        iconColor = TextSecondary,
                        iconBg = Background,
                        etiqueta = stringResource(R.string.historial_stat_proxima),
                        valor = stringResource(R.string.historial_stat_proxima_valor),
                        valorItalic = true
                    )
                }
            }

            // Buscador
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        stringResource(R.string.historial_buscar_placeholder),
                        color = TextHint,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null,
                        tint = TextHint, modifier = Modifier.size(20.dp))
                },
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Primary,
                    unfocusedBorderColor = Divider,
                    focusedContainerColor = Surface,
                    unfocusedContainerColor = Surface
                ),
                singleLine = true
            )

            // Filtros con scroll horizontal
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Surface)
                        .clickable { }
                        .padding(horizontal = 14.dp, vertical = 8.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.FilterList, contentDescription = null,
                            tint = TextSecondary, modifier = Modifier.size(14.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(
                            stringResource(R.string.historial_filtros_btn),
                            fontSize = 13.sp,
                            color = TextSecondary
                        )
                    }
                }

                filtros.forEach { filtro ->
                    val seleccionado = filtroActivo == filtro
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (seleccionado) Primary else Surface)
                            .clickable { filtroActivo = if (seleccionado) null else filtro }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            filtro,
                            fontSize = 13.sp,
                            color = if (seleccionado) Color.White else TextSecondary,
                            fontWeight = if (seleccionado) FontWeight.SemiBold else FontWeight.Normal
                        )
                    }
                }
            }

            // Encabezado resultados
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    stringResource(R.string.historial_registros_titulo),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Text(
                    stringResource(R.string.historial_resultados, vacunasFiltradas.size),
                    fontSize = 12.sp,
                    color = TextSecondary
                )
            }

            // Lista de vacunas
            vacunasFiltradas.forEach { vacuna ->
                VacunaCard(vacuna = vacuna)
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

// Card de vacuna expandible
@Composable
private fun VacunaCard(vacuna: Vacuna) {
    var expandida by remember { mutableStateOf(false) }

    val (bg, fg, label) = when (vacuna.esquema) {
        Esquema.COMPLETO   -> Triple(TagGreenBg,  TagGreenText,  stringResource(R.string.historial_esquema_completo))
        Esquema.INCOMPLETO -> Triple(TagOrangeBg, TagOrangeText, stringResource(R.string.historial_esquema_incompleto))
        Esquema.PENDIENTE  -> Triple(TagRedBg,    TagRedText,    stringResource(R.string.historial_esquema_pendiente))
    }

    val refuerzo = stringResource(R.string.historial_dosis_refuerzo)
    val unica    = stringResource(R.string.historial_dosis_unica)
    val dosisColor = when {
        vacuna.dosis.contains(refuerzo, ignoreCase = true) -> Primary
        vacuna.dosis.contains(unica,    ignoreCase = true) -> Primary
        else -> Warning
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            // Nombre + botón expandir
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        vacuna.nombre,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Accent,
                        modifier = Modifier.size(16.dp)
                    )
                }
                IconButton(
                    onClick = { expandida = !expandida },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        if (expandida) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(4.dp))

            // Fecha y dosis
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.CalendarMonth, contentDescription = null,
                    tint = TextHint, modifier = Modifier.size(13.dp))
                Text(vacuna.fecha, fontSize = 12.sp, color = TextSecondary)
                Text("•", fontSize = 12.sp, color = TextHint)
                Text(
                    vacuna.dosis,
                    fontSize = 12.sp,
                    color = dosisColor,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(10.dp))

            // Chip esquema
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(bg)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                Text(label, fontSize = 11.sp, color = fg, fontWeight = FontWeight.SemiBold)
            }

            // Detalle expandido
            if (expandida) {
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = Divider)
                Spacer(Modifier.height(10.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null,
                        tint = TextHint, modifier = Modifier.size(13.dp))
                    Text(vacuna.centro, fontSize = 12.sp, color = TextSecondary)
                }
            }
        }
    }
}

// Item de estadística
@Composable
private fun EstadisticaItem(
    icono: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    iconBg: Color,
    etiqueta: String,
    valor: String,
    valorItalic: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(icono, contentDescription = null,
                tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Text(etiqueta, fontSize = 10.sp, color = TextSecondary,
            fontWeight = FontWeight.SemiBold, letterSpacing = 0.5.sp)
        Text(
            valor,
            fontSize = 13.sp,
            color = TextPrimary,
            fontWeight = FontWeight.Bold,
            fontStyle = if (valorItalic) FontStyle.Italic else FontStyle.Normal
        )
    }
}