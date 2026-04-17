package com.example.vacugo.screens5

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
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

data class CentroSalud(
    val id: String,
    val nombre: String,
    val estado: String,
    val direccion: String,
    val telefono: String,
    val staff: Int,
    val cupos: Int,
    val stock: Int,
    val alertaStock: String? = null
)

@Composable
fun CentrosScreen(navController: NavController, username: String?) {
    var modoMapa by remember { mutableStateOf(false) }

    val centros = listOf(
        CentroSalud(
            id = "C-001", nombre = "Centro de Salud Norte", estado = stringResource(R.string.estado_activo),
            direccion = "Av. Libertador 4500, Buenos Aires", telefono = "+54 11 4832-9000",
            staff = 12, cupos = 250, stock = 45, alertaStock = stringResource(R.string.alerta_stock_critico, 45)
        ),
        CentroSalud(
            id = "C-002", nombre = "Unidad Sanitaria Sur", estado = stringResource(R.string.estado_mantenimiento),
            direccion = "Calle Falsa 123, Lomas de Zamor", telefono = "+54 11 4244-1122",
            staff = 0, cupos = 100, stock = 850
        )
    )

    Scaffold(
        bottomBar = { BottomMenu(
            navController = navController,
            username = username
        ) }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF9FAFB))
        ) {
            item {
                HeaderCentros()

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(24.dp),
                        color = Color(0xFFF3F4F6),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 4.dp)) {
                            BotonSelector(Icons.Outlined.ListAlt, stringResource(R.string.selector_lista), !modoMapa) { modoMapa = false }
                            BotonSelector(Icons.Outlined.Map, stringResource(R.string.selector_mapa), modoMapa) { modoMapa = true }
                        }
                    }

                    Button(
                        onClick = { navController.navigate("centroForm/${username ?: "Admin"}") },
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = azulito),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Icon(Icons.Default.Add, null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(stringResource(R.string.btn_nuevo), fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }

                Row(modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Timeline, null, tint = azulito, modifier = Modifier.size(20.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.section_centros_registrados, centros.size), color = Color.Gray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
                Spacer(Modifier.height(16.dp))
            }

            items(centros) { centro ->
                CentroCard(centro)
                Spacer(Modifier.height(16.dp))
            }

            item {
                ConsejoAdminCard()
                Spacer(Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun BotonSelector(icon: ImageVector, text: String, selected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (selected) Color.White else Color.Transparent,
        modifier = Modifier.height(40.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(icon, null, tint = if (selected) azulito else Color.Gray, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(text, color = if (selected) azulito else Color.Gray, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun CentroCard(centro: CentroSalud) {
    val colorEstado = if (centro.estado == stringResource(R.string.estado_activo)) Color(0xFF22C55E) else Color(0xFFEA580C)

    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(1.dp),
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Surface(color = Color(0xFFF3F4F6), shape = RoundedCornerShape(8.dp)) {
                        Text(centro.id, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), color = Color.Gray, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(6.dp).background(colorEstado, CircleShape))
                        Spacer(Modifier.width(6.dp))
                        Text(centro.estado, color = colorEstado, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
                Icon(Icons.Default.MoreVert, null, tint = Color.Gray)
            }

            Spacer(Modifier.height(12.dp))
            Text(centro.nombre, fontSize = 20.sp, fontWeight = FontWeight.Black)

            Spacer(Modifier.height(12.dp))
            DatoContactoItem(Icons.Outlined.LocationOn, centro.direccion)
            Spacer(Modifier.height(8.dp))
            DatoContactoItem(Icons.Outlined.Call, centro.telefono)

            centro.alertaStock?.let { mensaje ->
                Spacer(Modifier.height(16.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFEF2F2),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFFECACA))
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.ErrorOutline, null, tint = Color(0xFFEF4444), modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(mensaje, color = Color(0xFFB91C1C), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            } ?: run {
                if (centro.staff == 0) {
                    Spacer(Modifier.height(16.dp))
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        color = Color(0xFFF0FDF4),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(stringResource(R.string.msg_sin_personal), modifier = Modifier.padding(12.dp), color = Color(0xFF16A34A), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                IndicadorCentro(Modifier.weight(1f), Icons.Outlined.People, stringResource(R.string.label_staff), centro.staff.toString())
                IndicadorCentro(Modifier.weight(1f), Icons.Outlined.Timeline, stringResource(R.string.label_cupos), centro.cupos.toString())
                IndicadorCentro(Modifier.weight(1f), Icons.Outlined.Inventory2, stringResource(R.string.label_stock), centro.stock.toString())
            }

            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = Color(0xFFE5E7EB))

            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.btn_ver_detalles), color = azulito, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Icon(Icons.Outlined.ExpandMore, null, tint = azulito)
            }
        }
    }
}

@Composable
fun IndicadorCentro(modifier: Modifier, icon: ImageVector, label: String, value: String) {
    Surface(
        modifier = modifier,
        color = Color(0xFFF9FAFB),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, Color(0xFFE5E7EB))
    ) {
        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(icon, null, tint = azulito, modifier = Modifier.size(20.dp))
            Spacer(Modifier.height(4.dp))
            Text(label, color = Color.Gray, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            Text(value, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun DatoContactoItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, tint = azulito, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text(text, color = Color.Gray, fontSize = 13.sp)
    }
}

@Composable
fun HeaderCentros() {
    Surface(color = Color.White, shadowElevation = 1.dp) {
        Row(modifier = Modifier.fillMaxWidth().padding(top = 40.dp, bottom = 12.dp, start = 16.dp, end = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(36.dp).background(azulito, CircleShape), contentAlignment = Alignment.Center) {
                Icon(Icons.Outlined.Shield, null, tint = Color.White, modifier = Modifier.size(18.dp))
            }
            Spacer(Modifier.width(12.dp))
            Text(stringResource(R.string.centros_header_title), fontSize = 18.sp, fontWeight = FontWeight.ExtraBold)
        }
    }
}

@Composable
fun ConsejoAdminCard() {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(Icons.Outlined.CheckCircle, null, tint = azulito, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(12.dp))
            Column {
                Text(stringResource(R.string.consejo_admin_title), color = azulito, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(4.dp))
                Text(stringResource(R.string.consejo_admin_desc), color = Color.Gray, fontSize = 12.sp)
            }
        }
    }
}
