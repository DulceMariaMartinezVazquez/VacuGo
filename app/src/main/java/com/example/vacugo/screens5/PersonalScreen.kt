package com.example.vacugo.screens5

import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.navigation.NavController
import androidx.compose.ui.res.stringResource
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*

@Composable
fun PersonalScreen(
    navController: NavController,
    username: String?
) {

    var paginaActual by remember { mutableStateOf(1) }
    val listState = rememberLazyListState()

    LaunchedEffect(paginaActual) {
        listState.scrollToItem(0)
    }

    val doctores = List(20) {
        Doctor(
            nombre = stringResource(id = R.string.doctor_nombre, it),
            especialidad = stringResource(id = R.string.especialidad),
            activo = it % 2 == 0,
            cedula = stringResource(id = R.string.cedula_num, it),
            hospital = stringResource(id = R.string.hospital)
        )
    }

    val listaActual = doctores.chunked(10)[paginaActual - 1]

    Scaffold(
        topBar = { PersonalHeader() },
        bottomBar = { BottomMenu(
            navController = navController,
            username = username
        ) },
        containerColor = Color.White
    ) { padding ->

        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            item { StatsRow() }
            item { Spacer(Modifier.height(16.dp)) }

            item { BuscadorPersonal() }
            item { Spacer(Modifier.height(16.dp)) }

            item { TituloMedicos() }
            item { Spacer(Modifier.height(12.dp)) }

            items(listaActual) { doc ->
                DoctorItem(doc)
                Spacer(Modifier.height(16.dp))
            }

            item {
                PaginacionDoc(
                    paginaActual = paginaActual,
                    totalPaginas = 2,
                    onPaginaChange = { paginaActual = it }
                )
            }

            item { EspecialistasCentro() }
        }
    }
}


data class Doctor(
    val nombre: String,
    val especialidad: String,
    val activo: Boolean,
    val cedula: String,
    val hospital: String
)

data class Centro(
    val nombre: String,
    val especialistas: Int
)
enum class StatType {
    ACTIVO,
    INACTIVO,
    TOTAL
}

@Composable
fun PersonalHeader() {
    Surface(color = Color.White, shadowElevation = 4.dp) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, start = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                Modifier
                    .size(36.dp)
                    .background(azulito, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Shield, null, tint = Color.White)
            }

            Spacer(Modifier.width(12.dp))

            Text(
                stringResource(id = R.string.gestion_personal),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun StatsRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            valor = "24",
            label = stringResource(id = R.string.activos),
            icon = Icons.Outlined.CheckCircle,
            type = StatType.ACTIVO,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            valor = "06",
            label = stringResource(id = R.string.inactivos),
            icon = Icons.Outlined.Cancel,
            type = StatType.INACTIVO,
            modifier = Modifier.weight(1f)
        )

        StatCard(
            valor = "30",
            label = stringResource(id = R.string.total),
            icon = Icons.Outlined.Groups,
            type = StatType.TOTAL,
            modifier = Modifier.weight(1f)
        )
    }
}


@Composable
fun BuscadorPersonal() {
    var texto by remember { mutableStateOf("") }

    OutlinedTextField(
        value = texto,
        onValueChange = { texto = it },
        placeholder = {
            Text(
                stringResource(id = R.string.buscar_personal),
                color = Color(0xFFB0B0B0)
            )
        },
        leadingIcon = { Icon(Icons.Outlined.Search, null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color(0xFFE5E7EB),
            unfocusedIndicatorColor = Color(0xFFE5E7EB)
        )
    )
}


@Composable
fun TituloMedicos() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Outlined.MedicalServices,
            contentDescription = null,
            tint = AzulCielo,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            stringResource(id = R.string.listado_medicos),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp
        )
    }
}


@Composable
fun DoctorItem(doc: Doctor) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {

            Box(
                Modifier
                    .size(50.dp)
                    .background(Color.LightGray, CircleShape)
            )

            Spacer(Modifier.width(12.dp))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        doc.nombre,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            Modifier
                                .size(8.dp)
                                .background(
                                    if (doc.activo) Color(0xFF22C55E) else Color.Red,
                                    CircleShape
                                )
                        )

                        Spacer(Modifier.width(6.dp))

                        Text(
                            if (doc.activo)
                                stringResource(id = R.string.activo)
                            else
                                stringResource(id = R.string.inactivo),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(Modifier.height(4.dp))

                Text(
                    doc.especialidad,
                    color = AzulCielo,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    stringResource(id = R.string.cedula, doc.cedula),
                    color = Color.Gray,
                    fontSize = 12.sp
                )

                Spacer(Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {

                    Icon(
                        Icons.Outlined.LocalHospital,
                        null,
                        tint = Color.Black,
                        modifier = Modifier.size(14.dp)
                    )

                    Spacer(Modifier.width(6.dp))

                    Text(
                        doc.hospital,
                        color = Color.Black,
                        fontSize = 12.sp
                    )
                }

                Spacer(Modifier.height(10.dp))

                Divider(color = Color(0xFFE5E7EB))

                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                        OutlinedButton(
                            onClick = {},
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(Color.Black)
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Edit,
                                null,
                                tint = Color.Black,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(stringResource(id = R.string.editar), fontSize = 10.sp)
                        }

                        OutlinedButton(
                            onClick = {},
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                brush = androidx.compose.ui.graphics.SolidColor(Color.Black)
                            ),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color.Black
                            ),
                            contentPadding = PaddingValues(horizontal = 10.dp, vertical = 0.dp),
                            modifier = Modifier.height(30.dp)
                        ) {
                            Icon(
                                Icons.Outlined.Lock,
                                null,
                                tint = Color.Black,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(stringResource(id = R.string.asignar), fontSize = 10.sp)
                        }
                    }

                    Icon(
                        imageVector = Icons.Outlined.PersonRemove,
                        contentDescription = null,
                        tint = Color(0xFFEF4444),
                        modifier = Modifier
                            .size(20.dp)
                            .offset(y = (-2).dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PaginacionDoc(
    paginaActual: Int,
    totalPaginas: Int,
    onPaginaChange: (Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedButton(
                onClick = { onPaginaChange(paginaActual - 1) },
                enabled = paginaActual > 1,
                border = BorderStroke(1.dp, Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(stringResource(id = R.string.anterior), fontSize = 12.sp)
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {

                for (i in 1..totalPaginas) {

                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .background(
                                if (i == paginaActual) azulito else Color.Transparent,
                                CircleShape
                            )
                            .clickable { onPaginaChange(i) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = i.toString(),
                            fontSize = 12.sp,
                            color = if (i == paginaActual) Color.White else Color.Black
                        )
                    }
                }
            }

            OutlinedButton(
                onClick = { onPaginaChange(paginaActual + 1) },
                enabled = paginaActual < totalPaginas,
                border = BorderStroke(1.dp, Color.LightGray),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
            ) {
                Text(stringResource(id = R.string.siguiente), fontSize = 12.sp)
            }
        }
    }
}

@Composable
fun EspecialistasCentro() {

    val centros = listOf(
        Centro(stringResource(id = R.string.hospital_general), 12),
        Centro( stringResource(id = R.string.clinica_imss), 8)
    )

    val maxEspecialistas = 20f

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(
                Color(0xFFEAF4FF),
                RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Outlined.AccountBalance,
                contentDescription = null,
                tint = AzulCielo,
                modifier = Modifier.size(18.dp)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                stringResource(id = R.string.especialistas_centro),
                color = AzulCielo,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(12.dp))

        centros.forEach { centro ->

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = centro.nombre,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = "${centro.especialistas} / 20",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .background(Color.LightGray, RoundedCornerShape(50))
                ) {

                    val progress = centro.especialistas / maxEspecialistas

                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(progress)
                            .background(AzulCielo, RoundedCornerShape(50))
                    )
                }
            }
        }
    }
}
@Composable
fun StatCard(
    valor: String,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    type: StatType,
    modifier: Modifier = Modifier
) {

    val (cardColor, iconColor) = when (type) {
        StatType.ACTIVO -> Color.White to Color.Black
        StatType.INACTIVO -> Color(0xFFFFEBEE) to Color(0xFFEF4444)
        StatType.TOTAL -> Color(0xFFE0F2FE) to AzulCielo
    }

    Card(
        modifier = modifier.height(70.dp), // 🔽 más corto
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 10.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(Color.White, CircleShape)
                        .border(1.dp, Color(0xFFE5E7EB), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconColor,
                        modifier = Modifier.size(14.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = valor,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.offset(x = (20).dp)
                )
            }

            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}