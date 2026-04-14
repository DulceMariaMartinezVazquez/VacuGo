package com.example.vacugo.screens5

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CentroFormScreen(
    navController: NavController,
    username: String?
) {
    var nombreCentro by remember { mutableStateOf("Hospital General Metropolitano") }
    var correo by remember { mutableStateOf("admin@hospital-central") }
    var calle by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val fondoGrisContenedor = Color(0xFFF9FAFB)
    val turquesaExito = Color(0xFF2DD4BF)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.centro_form_title), fontWeight = FontWeight.Black, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.centro_form_back_desc))
                    }
                },
                actions = {
                    Icon(Icons.Outlined.Info, null, tint = AzulCielo, modifier = Modifier.padding(end = 16.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(stringResource(R.string.section_datos_centro), fontWeight = FontWeight.Black, fontSize = 18.sp)
                Text(stringResource(R.string.section_datos_centro_sub), color = Color.Gray, fontSize = 13.sp)

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = fondoGrisContenedor),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InputVacuGo(
                            label = stringResource(R.string.label_nombre_centro),
                            value = nombreCentro,
                            onValueChange = { nombreCentro = it },
                            icon = Icons.Outlined.Business,
                            isSuccess = true,
                            successColor = turquesaExito
                        )

                        Spacer(Modifier.height(16.dp))

                        Text(stringResource(R.string.label_tipo_centro), fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.DarkGray)
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White,
                            border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                        ) {
                            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.ExpandMore, null, tint = Color.Gray)
                                Spacer(Modifier.width(12.dp))
                                Text(stringResource(R.string.tipo_centro_default), modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(stringResource(R.string.section_direccion), fontWeight = FontWeight.Black, fontSize = 18.sp)
                Text(stringResource(R.string.section_direccion_sub), color = Color.Gray, fontSize = 13.sp)

                Spacer(Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = fondoGrisContenedor),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InputVacuGo(stringResource(R.string.label_calle), calle, { calle = it }, placeholder = stringResource(R.string.placeholder_calle))
                        Spacer(Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            InputVacuGo(stringResource(R.string.label_numero), "", {}, placeholder = stringResource(R.string.placeholder_numero), modifier = Modifier.weight(1f))
                            InputVacuGo(stringResource(R.string.label_cp), "", {}, placeholder = stringResource(R.string.placeholder_cp), modifier = Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(16.dp))
                        InputVacuGo(stringResource(R.string.label_colonia), "", {}, placeholder = stringResource(R.string.placeholder_colonia))
                        Spacer(Modifier.height(16.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            InputVacuGo(stringResource(R.string.label_ciudad), "", {}, placeholder = stringResource(R.string.placeholder_ciudad), modifier = Modifier.weight(1f))
                            InputVacuGo(stringResource(R.string.label_estado), "", {}, placeholder = stringResource(R.string.placeholder_estado), modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(stringResource(R.string.section_geolocalizacion), fontWeight = FontWeight.Black, fontSize = 18.sp)
                Spacer(Modifier.height(16.dp))

                Box(modifier = Modifier.fillMaxWidth().height(200.dp)) {
                    Card(shape = RoundedCornerShape(24.dp), modifier = Modifier.fillMaxSize()) {
                        Box(Modifier.background(Color.LightGray)) // Placeholder del mapa
                    }
                    Surface(
                        modifier = Modifier.align(Alignment.TopEnd).padding(12.dp),
                        shape = RoundedCornerShape(8.dp),
                        color = Color.White
                    ) {
                        Row(Modifier.padding(horizontal = 8.dp, vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Outlined.Map, null, modifier = Modifier.size(16.dp), tint = AzulCielo)
                            Spacer(Modifier.width(4.dp))
                            Text(stringResource(R.string.map_satelital), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                Button(
                    onClick = { },
                    modifier = Modifier.fillMaxWidth().padding(top = 12.dp).height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF5659EB))
                ) {
                    Text(stringResource(R.string.btn_ajustar_pin), fontWeight = FontWeight.Bold)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(stringResource(R.string.section_contacto), fontWeight = FontWeight.Black, fontSize = 18.sp)
                Spacer(Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = fondoGrisContenedor),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        InputVacuGo(stringResource(R.string.label_telefono), "", {}, icon = Icons.Outlined.Call, placeholder = stringResource(R.string.placeholder_telefono))
                        Spacer(Modifier.height(16.dp))
                        InputVacuGo(stringResource(R.string.label_correo), correo, { correo = it }, icon = Icons.Outlined.Mail, error = stringResource(R.string.error_correo))
                        Spacer(Modifier.height(16.dp))

                        Text(stringResource(R.string.label_horario), fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            shape = RoundedCornerShape(16.dp),
                            color = Color.White
                        ) {
                            Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                                Text(stringResource(R.string.dias_laborales), modifier = Modifier.weight(1f))
                                Text(stringResource(R.string.rango_horario), color = AzulCielo, fontWeight = FontWeight.Black)
                                Icon(Icons.Outlined.ExpandMore, null, tint = Color.Gray)
                            }
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Button(
                    onClick = { /* Guardar */ },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2563EB))
                ) {
                    Icon(Icons.Outlined.Save, null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.btn_guardar), fontWeight = FontWeight.Black)
                }

                TextButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Close, null, tint = Color.Gray)
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.btn_cancelar), color = Color.Gray, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun InputVacuGo(
    label: String, value: String, onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier, icon: ImageVector? = null,
    placeholder: String = "", isSuccess: Boolean = false,
    successColor: Color = Color(0xFF2DD4BF), error: String? = null
) {
    Column(modifier = modifier) {
        Text(label, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Color.DarkGray)
        OutlinedTextField(
            value = value, onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
            placeholder = { Text(placeholder, color = Color.LightGray) },
            leadingIcon = icon?.let { { Icon(it, null, tint = Color.Gray) } },
            trailingIcon = {
                if (isSuccess) Icon(Icons.Outlined.CheckCircle, null, tint = successColor)
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = if (error != null) Color.Red else AzulCielo,
                unfocusedBorderColor = if (error != null) Color.Red.copy(0.5f) else Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )
        if (error != null) {
            Row(Modifier.padding(top = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.ErrorOutline, null, tint = Color.Red, modifier = Modifier.size(14.dp))
                Spacer(Modifier.width(4.dp))
                Text(error, color = Color.Red, fontSize = 11.sp)
            }
        }
    }
}

