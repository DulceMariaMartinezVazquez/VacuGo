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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*

@Composable
fun ProfesionalScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundMain)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.Vaccines, null, tint = BrandBlue, modifier = Modifier.size(30.dp))
                Text(stringResource(R.string.vista_profesional), fontWeight = FontWeight.ExtraBold, fontSize = 22.sp, modifier = Modifier.padding(start = 10.dp), color = TextDark)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = BgPurplePastel),
            shape = RoundedCornerShape(24.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(55.dp)) {
                        Box(modifier = Modifier.fillMaxSize().clip(CircleShape).background(Color(0xFFE5E7EB))) {
                            Icon(Icons.Default.Person, null, modifier = Modifier.align(Alignment.Center).size(35.dp), tint = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(stringResource(R.string.nombre_paciente1), fontWeight = FontWeight.ExtraBold, fontSize = 18.sp, color = TextDark)
                        Text(stringResource(R.string.vacuna_paciente1), color = TextGray, fontSize = 13.sp)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = BrandBlue.copy(alpha = 0.05f))
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column {
                        Text(stringResource(R.string.tiempo), fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
                        Text(stringResource(R.string.transcurrido), fontSize = 10.sp, color = TextGray, fontWeight = FontWeight.Bold)
                        Text("08:42", fontSize = 26.sp, fontWeight = FontWeight.Black, color = BrandBlue)
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    SmallActionBtn(stringResource(R.string.finalizar), TextDark)
                    Spacer(modifier = Modifier.width(10.dp))
                    SmallActionBtn(stringResource(R.string.siguiente7), BrandBlue)
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        PacienteFilaCard(stringResource(R.string.paciente2), "68 años", stringResource(R.string.alta), "1", stringResource(R.string.vacuna2), "12 min")
        PacienteFilaCard(stringResource(R.string.paciente3), "34 años", stringResource(R.string.media), "2", stringResource(R.string.vacuna3), "25 min")
        PacienteFilaCard(stringResource(R.string.paciente4), "12 años", stringResource(R.string.baja), "3", stringResource(R.string.vacuna4), "38 min")

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun PacienteFilaCard(nombre: String, edad: String, prioridad: String, pos: String, vacuna: String, tiempo: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, GrayLine)
    ) {
        Row(modifier = Modifier.height(IntrinsicSize.Min)) {
            Box(modifier = Modifier.fillMaxHeight().width(6.dp).background(BrandBlue))

            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        color = BgCyanPastel,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.size(45.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                stringResource(R.string.fila),
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = TextStatusCyan
                            )
                            Text(
                                pos,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextStatusCyan
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            nombre,
                            fontWeight = FontWeight.ExtraBold,
                            color = TextDark,
                            fontSize = 16.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(edad, fontSize = 11.sp, color = TextGray)

                            Surface(
                                color = if (prioridad == stringResource(R.string.alta)) BgRedPastel else BgGrayPastel,
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    prioridad,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                                    color = if (prioridad == stringResource(R.string.alta)) PriorityRedText else TextGray,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Surface(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), color = Color(0xFFF9FAFB), shape = RoundedCornerShape(10.dp), border = BorderStroke(1.dp, GrayLine)) {
                    Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.EditCalendar, null, modifier = Modifier.size(14.dp), tint = BrandBlue)
                        Text(vacuna, modifier = Modifier.padding(start = 6.dp), fontSize = 11.sp, fontWeight = FontWeight.Medium, color = TextDark)
                        Spacer(modifier = Modifier.weight(1f))
                        Text(tiempo, fontSize = 11.sp, color = TextGray)
                    }
                }

                Row(modifier = Modifier.padding(top = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ActionBtn(Icons.Outlined.ContentPaste, stringResource(R.string.expediente), Color.Black, Modifier.weight(1f))
                    ActionBtn(Icons.Outlined.Phone, stringResource(R.string.llamar), BrandBlue, Modifier.weight(1f))
                    ActionBtn(Icons.Outlined.PersonOff, stringResource(R.string.ausente), Color.Red, Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ActionBtn(icon: ImageVector, label: String, iconColor: Color, modifier: Modifier) {
    OutlinedButton(
        onClick = {},
        modifier = modifier.height(44.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, GrayLine),
        contentPadding = PaddingValues(0.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, modifier = Modifier.size(18.dp), tint = iconColor)
            Text(label, fontSize = 8.sp, color = TextDark, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun SmallActionBtn(text: String, color: Color) {
    OutlinedButton(
        onClick = {},
        modifier = Modifier.height(34.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, GrayLine),
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 0.dp)
    ) {
        Text(text, color = color, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}