package com.example.vacugo.screens3

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import coil.compose.AsyncImage
import com.example.vacugo.R
import com.example.vacugo.data.DatosEjemplo
import com.example.vacugo.ui.theme.*
import java.io.File

@Composable
fun ExpedienteScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    val paciente = DatosEjemplo.paciente

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Surface)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.expediente_back_desc),
                    tint = Primary
                )
            }
            Spacer(Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.expediente_titulo),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        if (tabSeleccionada == 0) {
            ExpedienteVista(
                paciente = paciente,
                onSolicitarEdicion = { tabSeleccionada = 1 }
            )
        } else {
            ExpedienteEditar(
                paciente = paciente,
                onCancelar = { tabSeleccionada = 0 }
            )
        }
    }
}

@Composable
fun ExpedienteVista(
    paciente: com.example.vacugo.data.Paciente,
    onSolicitarEdicion: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(PrimaryLight)
                        .border(3.dp, Primary.copy(alpha = 0.3f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = paciente.nombre.first().toString(),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )
                }
                Text(
                    paciente.nombre, fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = TextPrimary
                )
                Text(
                    stringResource(R.string.expediente_actualizado),
                    fontSize = 11.sp, color = TextHint
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    MiniChip(Icons.Default.Badge, paciente.curp, modifier = Modifier.weight(1f))
                    MiniChip(Icons.Default.Email, paciente.email, modifier = Modifier.weight(1f))
                }
            }
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_datos_personales), icono = Icons.Default.Person) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoVista(stringResource(R.string.expediente_campo_nombre), paciente.nombre, modifier = Modifier.weight(1f))
                CampoVista(stringResource(R.string.expediente_campo_fecha_nac), paciente.fechaNacimiento, modifier = Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoVista(stringResource(R.string.expediente_campo_curp), paciente.curp, modifier = Modifier.weight(1f))
                CampoVista(stringResource(R.string.expediente_campo_afiliacion), stringResource(R.string.expediente_afiliacion_valor), modifier = Modifier.weight(1f))
            }
            CampoVista(stringResource(R.string.expediente_campo_genero), paciente.genero)
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_contacto), icono = Icons.Default.Phone) {
            CampoVista(stringResource(R.string.expediente_campo_telefono), paciente.telefono)
            CampoVista(stringResource(R.string.expediente_campo_email), paciente.email)
            CampoVista(stringResource(R.string.expediente_campo_direccion), paciente.direccion)
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_medica), icono = Icons.Default.MedicalServices) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoVista(stringResource(R.string.expediente_campo_sangre), paciente.tipoSangre, modifier = Modifier.weight(1f))
                CampoVista(
                    stringResource(R.string.expediente_campo_alergias), text = "", modifier = Modifier.weight(1f),
                    customContent = {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            AlergiaChip(stringResource(R.string.expediente_alergia_penicilina))
                            AlergiaChip(stringResource(R.string.expediente_alergia_nueces))
                        }
                    }
                )
            }
            CampoVista(stringResource(R.string.expediente_campo_condiciones), paciente.condiciones)
            CampoVista(stringResource(R.string.expediente_campo_medicamentos), paciente.medicamentos)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(DangerLight)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    Icons.Default.Warning, contentDescription = null,
                    tint = Danger, modifier = Modifier.size(16.dp)
                )
                Text(
                    stringResource(R.string.expediente_alerta_penicilina),
                    fontSize = 11.sp, color = Danger, lineHeight = 16.sp
                )
            }
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_emergencia), icono = Icons.Default.ContactPhone) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CampoVista(
                    stringResource(R.string.expediente_campo_cont_nombre), paciente.contactoEmergenciaNombre,
                    modifier = Modifier.weight(1f)
                )
                CampoVista(
                    stringResource(R.string.expediente_campo_cont_parentesco), paciente.contactoEmergenciaParentesco,
                    modifier = Modifier.weight(1f)
                )
            }
            CampoVista(stringResource(R.string.expediente_campo_cont_telefono), paciente.contactoEmergenciaTelefono)
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_qr), icono = Icons.Default.QrCode) {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QrSimulado()
                    Text(
                        stringResource(R.string.expediente_qr_descripcion),
                        fontSize = 11.sp, color = TextSecondary,
                        textAlign = TextAlign.Center, lineHeight = 16.sp
                    )
                }
            }
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_historial_mod), icono = Icons.Default.History) {
            ModificacionItem(stringResource(R.string.expediente_mod_1_desc), stringResource(R.string.expediente_mod_1_fecha))
            HorizontalDivider(color = Divider)
            ModificacionItem(stringResource(R.string.expediente_mod_2_desc), stringResource(R.string.expediente_mod_2_fecha))
            HorizontalDivider(color = Divider)
            ModificacionItem(stringResource(R.string.expediente_mod_3_desc), stringResource(R.string.expediente_mod_3_fecha))
            Spacer(Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(
                    stringResource(R.string.expediente_solicitar_revision),
                    fontSize = 12.sp, color = Primary, fontWeight = FontWeight.Medium
                )
            }
            Text(
                stringResource(R.string.expediente_aviso_verificacion),
                fontSize = 11.sp, color = TextHint,
                textAlign = TextAlign.Center, lineHeight = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = onSolicitarEdicion,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text(
                stringResource(R.string.expediente_btn_solicitar_edicion),
                modifier = Modifier.padding(vertical = 4.dp),
                fontWeight = FontWeight.SemiBold
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}

@Composable
fun ExpedienteEditar(
    paciente: com.example.vacugo.data.Paciente,
    onCancelar: () -> Unit
) {
    val context = LocalContext.current
    var fotoUri by remember { mutableStateOf<Uri?>(null) }

    val fotoFile = remember { File(context.cacheDir, "foto_perfil_temp.jpg") }
    val fotoFileUri = remember {
        FileProvider.getUriForFile(context, "${context.packageName}.provider", fotoFile)
    }

    val camaraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { exito ->
        if (exito) fotoUri = fotoFileUri
    }

    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { otorgado ->
        if (otorgado) camaraLauncher.launch(fotoFileUri)
    }

    var nombre       by remember { mutableStateOf(paciente.nombre) }
    var fechaNac     by remember { mutableStateOf(paciente.fechaNacimiento) }
    var curp         by remember { mutableStateOf(paciente.curp) }
    var afiliacion   by remember { mutableStateOf(context.getString(R.string.expediente_afiliacion_valor)) }
    var genero       by remember { mutableStateOf(paciente.genero) }
    var telefono     by remember { mutableStateOf(paciente.telefono) }
    var email        by remember { mutableStateOf(paciente.email) }
    var direccion    by remember { mutableStateOf(paciente.direccion) }
    var tipoSangre   by remember { mutableStateOf(paciente.tipoSangre) }
    var alergias     by remember { mutableStateOf(paciente.alergias) }
    var condiciones  by remember { mutableStateOf(paciente.condiciones) }
    var medicamentos by remember { mutableStateOf(paciente.medicamentos) }
    var contNombre   by remember { mutableStateOf(paciente.contactoEmergenciaNombre) }
    var contParent   by remember { mutableStateOf(paciente.contactoEmergenciaParentesco) }
    var contTel      by remember { mutableStateOf(paciente.contactoEmergenciaTelefono) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Surface)
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                        .clickable { permisoLauncher.launch(Manifest.permission.CAMERA) },
                    contentAlignment = Alignment.Center
                ) {
                    if (fotoUri != null) {
                        AsyncImage(
                            model = fotoUri,
                            contentDescription = stringResource(R.string.expediente_foto_desc),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .border(3.dp, Primary.copy(alpha = 0.3f), CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(88.dp)
                                .clip(CircleShape)
                                .background(PrimaryLight)
                                .border(3.dp, Primary.copy(alpha = 0.3f), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = paciente.nombre.first().toString(),
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Primary
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = stringResource(R.string.expediente_camara_desc),
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
                Text(
                    paciente.nombre, fontSize = 18.sp,
                    fontWeight = FontWeight.Bold, color = TextPrimary
                )
                Text(
                    stringResource(R.string.expediente_toca_foto),
                    fontSize = 11.sp, color = TextHint
                )
            }
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_datos_personales), icono = Icons.Default.Person) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CampoEditable(stringResource(R.string.expediente_campo_nombre), nombre, { nombre = it }, modifier = Modifier.weight(1f))
                CampoEditable(stringResource(R.string.expediente_campo_fecha_nac), fechaNac, { fechaNac = it }, modifier = Modifier.weight(1f))
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CampoEditable(stringResource(R.string.expediente_campo_curp), curp, { curp = it }, modifier = Modifier.weight(1f))
                CampoEditable(stringResource(R.string.expediente_campo_afiliacion), afiliacion, { afiliacion = it }, modifier = Modifier.weight(1f))
            }
            CampoEditable(stringResource(R.string.expediente_campo_genero), genero, { genero = it })
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_contacto), icono = Icons.Default.Phone) {
            CampoEditable(stringResource(R.string.expediente_campo_telefono), telefono, { telefono = it })
            CampoEditable(stringResource(R.string.expediente_campo_email), email, { email = it })
            CampoEditable(stringResource(R.string.expediente_campo_direccion), direccion, { direccion = it }, singleLine = false)
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_medica), icono = Icons.Default.MedicalServices) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CampoEditable(stringResource(R.string.expediente_campo_sangre), tipoSangre, { tipoSangre = it }, modifier = Modifier.weight(1f))
                CampoEditable(stringResource(R.string.expediente_campo_alergias), alergias, { alergias = it }, modifier = Modifier.weight(1f))
            }
            CampoEditable(stringResource(R.string.expediente_campo_condiciones), condiciones, { condiciones = it }, singleLine = false)
            CampoEditable(stringResource(R.string.expediente_campo_medicamentos), medicamentos, { medicamentos = it }, singleLine = false)
        }

        SeccionCard(titulo = stringResource(R.string.expediente_seccion_emergencia), icono = Icons.Default.ContactPhone) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CampoEditable(stringResource(R.string.expediente_campo_cont_nombre), contNombre, { contNombre = it }, modifier = Modifier.weight(1f))
                CampoEditable(stringResource(R.string.expediente_campo_cont_parentesco), contParent, { contParent = it }, modifier = Modifier.weight(1f))
            }
            CampoEditable(stringResource(R.string.expediente_campo_cont_telefono), contTel, { contTel = it })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
                .background(WarningLight)
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(Icons.Default.Info, contentDescription = null,
                tint = Warning, modifier = Modifier.size(16.dp))
            Text(
                stringResource(R.string.expediente_aviso_revision),
                fontSize = 11.sp, color = Color(0xFF92400E), lineHeight = 16.sp
            )
        }

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Primary)
        ) {
            Text(
                stringResource(R.string.expediente_btn_guardar),
                modifier = Modifier.padding(vertical = 4.dp),
                fontWeight = FontWeight.SemiBold
            )
        }
        OutlinedButton(
            onClick = onCancelar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = TextSecondary)
        ) {
            Text(
                stringResource(R.string.expediente_btn_cancelar),
                modifier = Modifier.padding(vertical = 4.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
    }
}

// ─────────────────────────────────────────────────────────────────────────────
// COMPONENTES REUTILIZABLES
// ─────────────────────────────────────────────────────────────────────────────

@Composable
private fun SeccionCard(
    titulo: String,
    icono: ImageVector,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    Modifier
                        .size(28.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryLight),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icono, contentDescription = null,
                        tint = Primary, modifier = Modifier.size(16.dp))
                }
                Text(titulo, fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold, color = TextPrimary)
            }
            HorizontalDivider(color = Divider)
            content()
        }
    }
}

@Composable
private fun CampoVista(
    label: String,
    text: String,
    modifier: Modifier = Modifier,
    customContent: (@Composable () -> Unit)? = null
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(label, fontSize = 10.sp, color = TextHint, fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp)
        if (customContent != null) {
            customContent()
        } else {
            Text(text, fontSize = 13.sp, color = TextPrimary, fontWeight = FontWeight.Medium)
        }
    }
}

@Composable
private fun CampoEditable(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, fontSize = 10.sp, color = TextHint, fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp)
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = singleLine,
            shape = RoundedCornerShape(10.dp),
            textStyle = LocalTextStyle.current.copy(fontSize = 13.sp, color = TextPrimary),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Primary,
                unfocusedBorderColor = Divider,
                focusedContainerColor = Surface,
                unfocusedContainerColor = Background
            )
        )
    }
}

@Composable
private fun AlergiaChip(texto: String) {
    Box(
        Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(TagRedBg)
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(texto, fontSize = 10.sp, color = TagRedText, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun MiniChip(icono: ImageVector, texto: String, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Background)
            .padding(horizontal = 8.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icono, contentDescription = null,
            tint = TextSecondary, modifier = Modifier.size(12.dp))
        Text(texto, fontSize = 10.sp, color = TextSecondary,
            maxLines = 1, overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis)
    }
}

@Composable
private fun ModificacionItem(descripcion: String, fecha: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(descripcion, fontSize = 13.sp, color = TextPrimary)
        Text(fecha, fontSize = 11.sp, color = TextHint)
    }
}

@Composable
private fun QrSimulado() {
    Box(
        modifier = Modifier
            .size(100.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Surface)
            .border(1.dp, Divider, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            repeat(7) { row ->
                Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                    repeat(7) { col ->
                        val filled = when {
                            row < 3 && col < 3 -> true
                            row < 3 && col > 3 -> true
                            row > 3 && col < 3 -> true
                            row == 3 && col == 3 -> true
                            row % 2 == 0 && col % 2 == 0 -> true
                            else -> false
                        }
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(RoundedCornerShape(1.dp))
                                .background(if (filled) TextPrimary else Color.Transparent)
                        )
                    }
                }
            }
        }
    }
}