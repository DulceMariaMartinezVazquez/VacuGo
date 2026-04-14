package com.example.vacugo.screens3

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*
import java.util.Calendar

// ── Modelos locales ───────────────────────────────────────────────────────────
private enum class EstadoCita { CONFIRMADA, VENCIDA }

private data class Cita(
    val vacuna: String,
    val centro: String,
    val fecha: String,
    val hora: String,
    val estado: EstadoCita
)

private val tiposVacunaRes = listOf(
    R.string.vacuna_influenza,
    R.string.vacuna_tetanos,
    R.string.vacuna_hepatitis_b,
    R.string.vacuna_dtp,
    R.string.vacuna_covid,
    R.string.vacuna_mmr
)

private val centrosVacunacionRes = listOf(
    R.string.centro_salud_norte,
    R.string.centro_hospital_clinicas,
    R.string.centro_san_juan,
    R.string.centro_clinica_valle
)

private val MESES_RES = listOf(
    R.string.mes_enero, R.string.mes_febrero, R.string.mes_marzo,
    R.string.mes_abril, R.string.mes_mayo,    R.string.mes_junio,
    R.string.mes_julio, R.string.mes_agosto,  R.string.mes_septiembre,
    R.string.mes_octubre, R.string.mes_noviembre, R.string.mes_diciembre
)

private fun primerDiaMes(anio: Int, mes: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(anio, mes, 1)
    return cal.get(Calendar.DAY_OF_WEEK) - 1
}

private fun diasEnMes(anio: Int, mes: Int): Int {
    val cal = Calendar.getInstance()
    cal.set(anio, mes, 1)
    return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
}

private fun abrirCalendarioSistema(context: Context, titulo: String, centro: String) {
    val cal = Calendar.getInstance().apply { set(2024, Calendar.MAY, 3, 9, 0) }
    val intent = Intent(Intent.ACTION_INSERT).apply {
        data = CalendarContract.Events.CONTENT_URI
        putExtra(CalendarContract.Events.TITLE, context.getString(R.string.calendario_evento_prefijo, titulo))
        putExtra(CalendarContract.Events.EVENT_LOCATION, centro)
        putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.timeInMillis)
        putExtra(CalendarContract.EXTRA_EVENT_END_TIME, cal.timeInMillis + 3600000L)
    }
    context.startActivity(intent)
}

// ── Pantalla ──────────────────────────────────────────────────────────────────
@Composable
fun CalendarioScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val activity = context as? Activity
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    val tiposVacuna        = tiposVacunaRes.map { stringResource(it) }
    val centrosVacunacion  = centrosVacunacionRes.map { stringResource(it) }
    val meses              = MESES_RES.map { stringResource(it) }

    val proximasCitas = listOf(
        Cita(stringResource(R.string.cita_pentavalente), stringResource(R.string.centro_salud_r024),
            stringResource(R.string.fecha_pentavalente), stringResource(R.string.hora_1030),
            EstadoCita.CONFIRMADA),
        Cita(stringResource(R.string.cita_hepatitis_a),  stringResource(R.string.centro_salud_norte),
            stringResource(R.string.fecha_hepatitis_a),  stringResource(R.string.hora_0900),
            EstadoCita.VENCIDA),
        Cita(stringResource(R.string.cita_rotavirus),    stringResource(R.string.centro_hospital_cenera),
            stringResource(R.string.fecha_rotavirus),    stringResource(R.string.hora_1100),
            EstadoCita.CONFIRMADA),
    )

    var sincronizar        by remember { mutableStateOf(false) }
    var diaSeleccionado    by remember { mutableStateOf(13) }
    var tipoVacuna         by remember { mutableStateOf(tiposVacuna[3]) }
    var centroSeleccionado by remember { mutableStateOf(centrosVacunacion[0]) }
    var fechaCita          by remember { mutableStateOf(context.getString(R.string.form_fecha_default)) }
    var horaCita           by remember { mutableStateOf(context.getString(R.string.form_hora_default)) }
    var mesActual          by remember { mutableStateOf(5) }
    var anioActual         by remember { mutableStateOf(2024) }

    val diasConfirmados  = setOf(10, 20)
    val diasRecomendados = setOf(4, 11)
    val diasVencidos     = setOf(13, 18)

    val diasSemana = listOf(
        stringResource(R.string.dia_dom), stringResource(R.string.dia_lun),
        stringResource(R.string.dia_mar), stringResource(R.string.dia_mie),
        stringResource(R.string.dia_jue), stringResource(R.string.dia_vie),
        stringResource(R.string.dia_sab)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // ── Toolbar ───────────────────────────────────────────────────
        Surface(color = Surface, shadowElevation = 2.dp) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.calendario_back_desc),
                            tint = Primary
                        )
                    }
                    Box(
                        modifier = Modifier.size(30.dp).clip(CircleShape).background(Primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.CalendarMonth, null,
                            tint = Color.White, modifier = Modifier.size(17.dp))
                    }
                    Column {
                        Text(
                            stringResource(R.string.calendario_titulo),
                            fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextPrimary
                        )
                        Text(
                            stringResource(R.string.calendario_subtitulo),
                            fontSize = 10.sp, color = TextSecondary
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        Icons.Default.Sync, null,
                        tint = if (sincronizar) Primary else TextHint,
                        modifier = Modifier.size(15.dp)
                    )
                    Text(
                        stringResource(R.string.calendario_sincronizar),
                        fontSize = 11.sp, color = TextSecondary
                    )
                    Switch(
                        checked = sincronizar,
                        onCheckedChange = { checked ->
                            sincronizar = checked
                            if (checked) abrirCalendarioSistema(context, tipoVacuna, centroSeleccionado)
                        },
                        modifier = Modifier.height(24.dp),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Surface,
                            checkedTrackColor = Primary
                        )
                    )
                }
            }
        }

        // ── Cuerpo 3 columnas ─────────────────────────────────────────
        Row(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding()
                .padding(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            // ── COL 1: Calendario ─────────────────────────────────────
            Card(
                modifier = Modifier.weight(1.2f).fillMaxHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    // Navegación mes
                    Row(
                        modifier = Modifier.fillMaxWidth().height(36.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                if (mesActual == 0) { mesActual = 11; anioActual-- }
                                else mesActual--
                                diaSeleccionado = 1
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.ChevronLeft, null, tint = Primary)
                        }
                        Text(
                            "${meses[mesActual]} $anioActual",
                            fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary
                        )
                        IconButton(
                            onClick = {
                                if (mesActual == 11) { mesActual = 0; anioActual++ }
                                else mesActual++
                                diaSeleccionado = 1
                            },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Icon(Icons.Default.ChevronRight, null, tint = Primary)
                        }
                    }

                    // Encabezados días
                    Row(
                        modifier = Modifier.fillMaxWidth().height(24.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        diasSemana.forEach { d ->
                            Text(
                                d,
                                modifier = Modifier.weight(1f),
                                textAlign = TextAlign.Center,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextHint
                            )
                        }
                    }

                    HorizontalDivider(
                        color = Divider, thickness = 0.5.dp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )

                    // ── Grid de días ──────────────────────────────────
                    val offset      = primerDiaMes(anioActual, mesActual)
                    val totalDias   = diasEnMes(anioActual, mesActual)
                    val esJunio2024 = mesActual == 5 && anioActual == 2024

                    val celdas: List<Int?> =
                        List(offset) { null } + (1..totalDias).map { it }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(7),
                        modifier = Modifier.fillMaxWidth().weight(1f),
                        userScrollEnabled = false,
                        verticalArrangement = Arrangement.spacedBy(2.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        items(celdas) { num ->
                            if (num == null) {
                                Box(Modifier.aspectRatio(1f))
                            } else {
                                Box(
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                num == diaSeleccionado -> Primary
                                                esJunio2024 && num in diasConfirmados  -> AccentLight
                                                esJunio2024 && num in diasRecomendados -> TagOrangeBg
                                                esJunio2024 && num in diasVencidos     -> DangerLight
                                                else -> Color.Transparent
                                            }
                                        )
                                        .clickable { diaSeleccionado = num },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        "$num",
                                        fontSize = 11.sp,
                                        fontWeight = if (num == diaSeleccionado)
                                            FontWeight.Bold else FontWeight.Normal,
                                        color = when {
                                            num == diaSeleccionado -> Color.White
                                            esJunio2024 && num in diasConfirmados -> Accent
                                            esJunio2024 && num in diasVencidos    -> Danger
                                            else -> TextPrimary
                                        },
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }


                }
            }

            // ── COL 2: Próximas citas ─────────────────────────────────
            Card(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        stringResource(R.string.citas_titulo),
                        fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = TextPrimary
                    )
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        proximasCitas.forEach { cita -> CitaItem(cita = cita) }
                    }
                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text(
                            stringResource(R.string.citas_agendar),
                            fontSize = 13.sp, modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                    Text(
                        stringResource(R.string.citas_recordatorio_email),
                        fontSize = 10.sp, color = TextHint,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // ── COL 3: Formulario ─────────────────────────────────────
            Card(
                modifier = Modifier.weight(1f).fillMaxHeight(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        stringResource(R.string.form_titulo),
                        fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextPrimary
                    )

                    FormularioCampo(stringResource(R.string.form_label_vacuna)) {
                        var expandido by remember { mutableStateOf(false) }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = tipoVacuna, onValueChange = {}, readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        if (expandido) Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                        null, tint = TextSecondary,
                                        modifier = Modifier.clickable { expandido = !expandido }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth().clickable { expandido = !expandido },
                                singleLine = true, shape = RoundedCornerShape(10.dp),
                                colors = campoColores()
                            )
                            DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                                tiposVacuna.forEach { tipo ->
                                    DropdownMenuItem(
                                        text = { Text(tipo, fontSize = 13.sp) },
                                        onClick = { tipoVacuna = tipo; expandido = false }
                                    )
                                }
                            }
                        }
                    }

                    FormularioCampo(stringResource(R.string.form_label_centro)) {
                        var expandido by remember { mutableStateOf(false) }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            OutlinedTextField(
                                value = centroSeleccionado, onValueChange = {}, readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        if (expandido) Icons.Default.KeyboardArrowUp
                                        else Icons.Default.KeyboardArrowDown,
                                        null, tint = TextSecondary,
                                        modifier = Modifier.clickable { expandido = !expandido }
                                    )
                                },
                                modifier = Modifier.fillMaxWidth().clickable { expandido = !expandido },
                                singleLine = true, shape = RoundedCornerShape(10.dp),
                                colors = campoColores()
                            )
                            DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false }) {
                                centrosVacunacion.forEach { centro ->
                                    DropdownMenuItem(
                                        text = { Text(centro, fontSize = 13.sp) },
                                        onClick = { centroSeleccionado = centro; expandido = false }
                                    )
                                }
                            }
                        }
                    }

                    FormularioCampo(stringResource(R.string.form_label_fecha_hora)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            OutlinedTextField(
                                value = fechaCita, onValueChange = { fechaCita = it },
                                modifier = Modifier.weight(2f), singleLine = true,
                                shape = RoundedCornerShape(10.dp), colors = campoColores()
                            )
                            OutlinedTextField(
                                value = horaCita, onValueChange = { horaCita = it },
                                modifier = Modifier.weight(1f), singleLine = true,
                                shape = RoundedCornerShape(10.dp), colors = campoColores()
                            )
                        }
                    }

                    Spacer(Modifier.height(4.dp))

                    Button(
                        onClick = { abrirCalendarioSistema(context, tipoVacuna, centroSeleccionado) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text(
                            stringResource(R.string.form_confirmar),
                            modifier = Modifier.padding(vertical = 4.dp),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Text(
                        stringResource(R.string.form_recordatorio_email),
                        fontSize = 10.sp, color = TextHint,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

// ── Componentes privados ──────────────────────────────────────────────────────

@Composable
private fun CitaItem(cita: Cita) {
    val barColor   = if (cita.estado == EstadoCita.VENCIDA) Danger else Accent
    val bgColor    = if (cita.estado == EstadoCita.VENCIDA) DangerLight else AccentLight
    val confirmada = cita.estado == EstadoCita.CONFIRMADA

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Ícono de estado
        Box(
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                if (cita.estado == EstadoCita.VENCIDA) Icons.Default.Warning
                else Icons.Default.CheckCircle,
                contentDescription = null,
                tint = barColor,
                modifier = Modifier.size(15.dp)
            )
        }

        // Nombre de vacuna + centro
        Column(modifier = Modifier.weight(1f)) {
            Text(
                cita.vacuna,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                cita.centro,
                fontSize = 10.sp,
                color = TextSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        // Fecha y hora
        Column(horizontalAlignment = Alignment.End) {
            Text(
                cita.fecha,
                fontSize = 10.sp,
                color = if (cita.estado == EstadoCita.VENCIDA) Danger else TextSecondary,
                maxLines = 1
            )
            Text(
                cita.hora,
                fontSize = 10.sp,
                color = TextHint,
                maxLines = 1
            )
        }

        // Checkbox de confirmación
        Box(
            Modifier
                .size(18.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(if (confirmada) AccentLight else Background)
                .border(1.dp, if (confirmada) Accent else Divider, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (confirmada) {
                Text("✓", fontSize = 10.sp, color = Accent, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun FormularioCampo(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, fontSize = 12.sp, fontWeight = FontWeight.Medium, color = TextSecondary)
        content()
    }
}

@Composable
private fun campoColores() = OutlinedTextFieldDefaults.colors(
    focusedBorderColor      = Primary,
    unfocusedBorderColor    = Divider,
    focusedContainerColor   = Surface,
    unfocusedContainerColor = Background,
    focusedTextColor        = TextPrimary,
    unfocusedTextColor      = TextPrimary
)