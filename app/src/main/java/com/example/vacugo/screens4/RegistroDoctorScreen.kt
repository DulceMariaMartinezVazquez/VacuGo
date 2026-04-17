package com.example.vacugo.screens4

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroDoctorScreen(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            Surface(
                color = Color.White,
                modifier = Modifier.statusBarsPadding().padding(top = 4.dp)
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(stringResource(id = R.string.registro_title), fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    },
                    navigationIcon = {
                        IconButton(onClick = onBack, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.Default.ChevronLeft, contentDescription = null, modifier = Modifier.size(24.dp))
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
                    modifier = Modifier.height(32.dp)
                )
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(top = paddingValues.calculateTopPadding())
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            SectionContainer(icon = Icons.Outlined.Person, title = stringResource(id = R.string.section_paciente)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(60.dp)) {
                        Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color.LightGray)) {
                            Icon(Icons.Default.Person, null, modifier = Modifier.align(Alignment.Center), tint = Color.White)
                        }
                        Surface(color = Color(0xFF22C55E), shape = CircleShape, modifier = Modifier.size(12.dp).align(Alignment.BottomEnd).border(2.dp, Color.White, CircleShape)) {}
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(stringResource(id = R.string.paciente_nombre1), fontWeight = FontWeight.Black, fontSize = 18.sp, color = TextDark)
                        Text(stringResource(id = R.string.paciente_id), color = TextGray, fontSize = 13.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                    InfoBox(label = stringResource(id = R.string.label_edad), value = stringResource(id = R.string.valor_edad), modifier = Modifier.weight(1f))
                    Spacer(modifier = Modifier.width(12.dp))
                    InfoBox(label = stringResource(id = R.string.label_sangre), value = stringResource(id = R.string.valor_sangre), modifier = Modifier.weight(1f))
                }
                Spacer(modifier = Modifier.height(12.dp))
                Surface(color = WarningRedBg, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(stringResource(id = R.string.label_alergias), fontSize = 10.sp, fontWeight = FontWeight.Bold, color = WarningRedText)
                        Text(stringResource(id = R.string.valor_alergias), color = WarningRedText, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionContainer(icon = Icons.Outlined.ShieldMoon, title = stringResource(id = R.string.section_detalles)) {
                Text(stringResource(id = R.string.label_vacuna_aplicar), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                CustomTextField(value = "", onValueChange = {}, placeholder = stringResource(id = R.string.placeholder_seleccionar), isDropdown = true)

                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(id = R.string.label_indicador_dosis), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    DoseChip(stringResource(id = R.string.dosis_1), false)
                    DoseChip(stringResource(id = R.string.dosis_2), false)
                    DoseChip(stringResource(id = R.string.dosis_refuerzo), true)
                    DoseChip(stringResource(id = R.string.dosis_anual), false)
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(id = R.string.label_lote), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(value = "MOD-2024-X99", onValueChange = {}, trailingIcon = Icons.Outlined.CheckCircle)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(color = InfoBlueBg, shape = RoundedCornerShape(12.dp), modifier = Modifier.size(52.dp)) {
                        Icon(Icons.Outlined.QrCodeScanner, null, modifier = Modifier.padding(14.dp), tint = ActionBlue)
                    }
                }
                Text(stringResource(id = R.string.lote_autocompletado), fontSize = 10.sp, color = TextGray, modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionContainer(icon = Icons.Outlined.Schedule, title = stringResource(id = R.string.section_administracion)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(stringResource(id = R.string.label_fecha), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        CustomTextField(value = "24/05/2024", onValueChange = {}, trailingIcon = Icons.Outlined.CalendarMonth)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(stringResource(id = R.string.label_hora), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                        CustomTextField(value = "14:35", onValueChange = {}, trailingIcon = Icons.Outlined.AccessTime)
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(stringResource(id = R.string.label_observaciones), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                CustomTextField(value = "", onValueChange = {}, placeholder = stringResource(id = R.string.placeholder_lugar), isLong = true)
            }

            Spacer(modifier = Modifier.height(16.dp))

            SectionContainer(icon = Icons.Default.Add, title = stringResource(id = R.string.section_firma)) {
                Text(stringResource(id = R.string.label_firma_responsable), fontSize = 11.sp, color = TextGray, modifier = Modifier.padding(bottom = 6.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    border = BorderStroke(1.dp, GrayLine),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(stringResource(id = R.string.firma_nombre), fontSize = 20.sp, color = ActionBlue.copy(alpha = 0.6f), fontStyle = FontStyle.Italic)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = GrayLine)
                        Text(stringResource(id = R.string.firma_footer), fontSize = 9.sp, color = TextGray)
                    }
                }

                Surface(
                    modifier = Modifier.padding(top = 16.dp),
                    color = InfoBlueBg.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, ActionBlue.copy(alpha = 0.2f))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(checked = true, onCheckedChange = {}, colors = CheckboxDefaults.colors(checkedColor = ActionBlue))
                            Text(stringResource(id = R.string.check_programar), color = ActionBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Text(stringResource(id = R.string.label_fecha_sugerida), fontSize = 13.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(bottom = 4.dp))
                        CustomTextField(value = "2024-06-24", onValueChange = {})

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(stringResource(id = R.string.label_tipo_dosis_sugerida), fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Row(modifier = Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            DoseChip(stringResource(id = R.string.dosis_sug_2), false)
                            DoseChip(stringResource(id = R.string.dosis_sug_refuerzo), true)
                            DoseChip(stringResource(id = R.string.dosis_sug_seguimiento), false)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandBlue)
            ) {
                Text(stringResource(id = R.string.btn_registrar), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }

            TextButton(
                onClick = {},
                modifier = Modifier.fillMaxWidth().padding(bottom = 0.dp),
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(stringResource(id = R.string.btn_cancelar7), color = TextGray, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "",
    isDropdown: Boolean = false,
    trailingIcon: ImageVector? = null,
    isLong: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, fontSize = 13.sp, color = TextGray) },
        modifier = Modifier
            .fillMaxWidth()
            .then(if (isLong) Modifier.height(80.dp) else Modifier.height(52.dp))
            .clip(RoundedCornerShape(12.dp)),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        trailingIcon = {
            if (isDropdown) Icon(Icons.Default.KeyboardArrowDown, null, tint = TextGray)
            else if (trailingIcon != null) Icon(trailingIcon, null, modifier = Modifier.size(20.dp), tint = TextGray)
        }
    )
}

@Composable
fun SectionContainer(icon: ImageVector, title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = InfoBlueBg.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(color = InfoBlueBg, shape = CircleShape, modifier = Modifier.size(30.dp)) {
                    Icon(icon, null, modifier = Modifier.padding(6.dp), tint = ActionBlue)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(title, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = TextDark)
            }
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
fun InfoBox(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(color = Color.White, shape = RoundedCornerShape(10.dp), modifier = modifier) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(label, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = TextGray)
            Text(value, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = TextDark)
        }
    }
}

@Composable
fun DoseChip(text: String, isSelected: Boolean) {
    Surface(
        color = if (isSelected) ActionBlue else Color.White,
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.clickable { }
    ) {
        Text(
            text = text, modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
            color = if (isSelected) Color.White else TextGray, fontSize = 11.sp, fontWeight = FontWeight.Medium
        )
    }
}
