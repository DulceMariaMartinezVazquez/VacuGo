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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.stringResource
import com.example.vacugo.R
import com.example.vacugo.ui.theme.*


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EstadisticasScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.estadisticas), fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = {}) { Icon(Icons.Default.ChevronLeft, null) }
                },
                actions = {
                    Surface(
                        modifier = Modifier.padding(end = 16.dp).size(36.dp),
                        color = StatsBlue,
                        shape = CircleShape
                    ) {
                        Icon(Icons.Default.Edit, contentDescription = null,
                            tint = Color.White, modifier = Modifier.padding(8.dp))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BgLight)
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            FiltrosSection()

            Row(
                modifier = Modifier.padding(16.dp).horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SummaryCard(stringResource(R.string.total_vacunados), "2.4M", "+12%", Icons.Outlined.People)
                SummaryCard(stringResource(R.string.esquema_completo), "84.2%", "+5.1%", Icons.Outlined.Timeline)
            }

            ChartSection(stringResource(R.string.vacunacion_edad), stringResource(R.string.dosis_aplicadas)) {
                Box(Modifier.height(180.dp).fillMaxWidth().background(Color.White)) {
                    Text(stringResource(R.string.grafico_lineal), Modifier.align(Alignment.Center), color = Color.LightGray)
                }
            }

            ChartSection(stringResource(R.string.distribucion_sexo), "") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(120.dp).padding(8.dp)) {
                        CircularProgressIndicator(progress = 0.52f, color = StatsBlue, strokeWidth = 12.dp, modifier = Modifier.fillMaxSize())
                        CircularProgressIndicator(progress = 0.45f, color = StatsOrange, strokeWidth = 12.dp, modifier = Modifier.fillMaxSize().padding(14.dp))
                    }
                    Column(Modifier.padding(start = 24.dp)) {
                        LegendItem(stringResource(R.string.mujeres), "52%", StatsBlue)
                        LegendItem(stringResource(R.string.hombres), "45%", StatsOrange)
                        LegendItem(stringResource(R.string.otros), "3%", StatsPurple)
                    }
                }
            }
            InsightsSection()

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Composable
fun FiltrosSection() {
    Surface(color = Color.White, modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Default.FilterAlt, contentDescription = null, tint = StatsBlue, modifier = Modifier.size(20.dp))
                Text(stringResource(R.string.filtros_activos), fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Text(stringResource(R.string.limpiar), color = StatsBlue, fontSize = 14.sp)
            }

            Text(stringResource(R.string.rango_edad), fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 16.dp))
            Slider(value = 0.4f, onValueChange = {}, colors = SliderDefaults.colors(thumbColor = Color.White, activeTrackColor = StatsBlue))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("0", fontSize = 11.sp)
                Text(stringResource(R.string.edad_rango_valor), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                Text("100+", fontSize = 11.sp)
            }

            Text(stringResource(R.string.sexo), fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
                FilterChipCustom(stringResource(R.string.todos), true)
                FilterChipCustom(stringResource(R.string.mujer), false)
                FilterChipCustom(stringResource(R.string.hombre), false)
                FilterChipCustom(stringResource(R.string.otro), false)
            }
        }
    }
}

@Composable
fun SummaryCard(title: String, value: String, percent: String, icon: ImageVector) {
    Card(
        modifier = Modifier.size(160.dp, 140.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, GrayBorder),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(shape = CircleShape, color = StatsBlue.copy(alpha = 0.1f), modifier = Modifier.size(32.dp)) {
                Icon(icon, null, tint = StatsBlue, modifier = Modifier.padding(6.dp))
            }
            Text(title, fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Black)
            Surface(shape = RoundedCornerShape(8.dp), color = BgLight, border = BorderStroke(1.dp, GrayBorder)) {
                Text(" ↗ $percent ", fontSize = 10.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(2.dp))
            }
        }
    }
}

@Composable
fun InsightsSection() {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(stringResource(R.string.insights_region), fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.ver_todos), color = StatsBlue, fontSize = 14.sp)
        }
        Spacer(Modifier.height(12.dp))
        RegionItem(stringResource(R.string.norte), stringResource(R.string.optimo), "92%", StatsGreen)
        RegionItem(stringResource(R.string.sur), stringResource(R.string.en_proceso), "78%", StatsBlue)
        RegionItem(stringResource(R.string.este), stringResource(R.string.critico), "45%", StatsOrange)
    }
}

@Composable
fun RegionItem(name: String, status: String, coverage: String, color: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, GrayBorder),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.size(40.dp).clip(CircleShape).background(Color.LightGray))
            Column(Modifier.padding(start = 12.dp).weight(1f)) {
                Text(name, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(Modifier.size(8.dp).clip(CircleShape).background(color))
                    Text(" $status", fontSize = 12.sp, color = Color.Gray)
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(coverage, fontWeight = FontWeight.Bold, color = color, fontSize = 18.sp)
                Text(stringResource(R.string.cobertura), fontSize = 10.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun LegendItem(label: String, value: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 2.dp)) {
        Box(Modifier.size(10.dp).clip(CircleShape).background(color))
        Text(" $label ", fontSize = 12.sp, color = Color.Gray)
        Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun FilterChipCustom(text: String, selected: Boolean) {
    Surface(
        color = if (selected) StatsBlue else Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, if (selected) StatsBlue else GrayBorder)
    ) {
        Text(text, color = if (selected) Color.White else Color.Black,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp), fontSize = 13.sp)
    }
}

@Composable
fun ChartSection(title: String, subtitle: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.padding(16.dp).fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, GrayBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            if (subtitle.isNotEmpty()) Text(subtitle, fontSize = 11.sp, color = Color.Gray)
            Spacer(Modifier.height(16.dp))
            content()
        }
    }
}