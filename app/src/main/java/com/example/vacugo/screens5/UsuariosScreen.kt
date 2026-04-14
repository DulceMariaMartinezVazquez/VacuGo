package com.example.vacugo.screens5

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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

@Composable
fun UsuariosScreen(
    navController: NavController,
    username: String?
) {

    var paginaActual by remember { mutableStateOf(1) }
    val listState = rememberLazyListState()

    LaunchedEffect(paginaActual) {
        listState.scrollToItem(0)
    }

    val usuariosPagina1 = listOf(
        "Alejandro Herrera García",
        "Alejandro Herrera García",
        "Alejandro Herrera García",
        "Carlos Ramírez López",
        "Luis Fernández Cruz"
    )

    val usuariosPagina2 = listOf(
        "María González Ruiz",
        "Fernanda Torres Díaz",
        "José Martínez Pérez",
        "Andrea López Vargas",
        "Miguel Sánchez Ortega"
    )

    val listaActual = if (paginaActual == 1) usuariosPagina1 else usuariosPagina2

    Scaffold(
        topBar = { UsuariosHeader() },
        bottomBar = {
            BottomMenu(
                currentRoute = "usuarios",
                navController = navController,
                username = username
            )
        },
        containerColor = Color.White
    ) { padding ->

        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {

            BuscadorUsuarios()

            Spacer(modifier = Modifier.height(16.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {

                MiniCardUsuarios(
                    stringResource(R.string.user_card_total_title),
                    stringResource(R.string.user_card_total_value),
                    Color(0xFFDDEBFF),
                    Color(0xFF2563EB),
                    Icons.Outlined.Group,
                    Modifier.weight(1f)
                )

                MiniCardUsuarios(
                    stringResource(R.string.user_card_active_title),
                    stringResource(R.string.user_card_active_value),
                    Color(0xFFD1FAE5),
                    Color(0xFF10B981),
                    Icons.Outlined.Person,
                    Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            ResultadosHeader()

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(state = listState) {

                items(listaActual.size) { index ->
                    UsuarioItem(nombre = listaActual[index])
                    Spacer(modifier = Modifier.height(16.dp))
                }

                item {
                    Paginacion(
                        paginaActual = paginaActual,
                        totalPaginas = 2,
                        onPaginaChange = { paginaActual = it }
                    )
                }
            }
        }
    }
}

@Composable
fun UsuariosHeader() {
    Surface(color = Color.White, shadowElevation = 4.dp) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 48.dp, bottom = 14.dp, start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(azulito, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Outlined.Shield, null, tint = Color.White, modifier = Modifier.size(20.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = stringResource(R.string.user_header_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun BuscadorUsuarios() {
    var texto by remember { mutableStateOf("") }

    OutlinedTextField(
        value = texto,
        onValueChange = { texto = it },
        placeholder = {
            Text(stringResource(R.string.user_search_placeholder), color = Negris)
        },
        leadingIcon = {
            Icon(Icons.Outlined.Search, null, tint = Negris)
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF1F5F9), RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color(0xFFF1F5F9),
            unfocusedContainerColor = Color(0xFFF1F5F9),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun MiniCardUsuarios(
    title: String,
    value: String,
    backgroundColor: Color,
    iconColor: Color,
    icon: ImageVector,
    modifier: Modifier
) {
    Card(
        modifier = modifier.height(130.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = iconColor)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(title, color = Color.Gray, fontSize = 12.sp)
            Text(value, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ResultadosHeader() {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(stringResource(R.string.user_results_label), fontSize = 12.sp, color = Color.Gray)

        Spacer(modifier = Modifier.width(8.dp))

        Box(
            modifier = Modifier
                .background(Color(0xFFE5E7EB), RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(stringResource(R.string.user_results_count), color = azulito, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(stringResource(R.string.user_results_view_limit), color = Color.Gray, fontSize = 12.sp)
        Text(stringResource(R.string.user_results_change), color = azulito, fontSize = 12.sp)
    }
}

@Composable
fun UsuarioItem(nombre: String) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.LightGray, CircleShape)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column {

                    Text(nombre, fontWeight = FontWeight.Bold)

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        stringResource(R.string.user_mock_curp),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color.Green, CircleShape)
                        )

                        Spacer(modifier = Modifier.width(6.dp))

                        Text(stringResource(R.string.user_status_active), fontSize = 12.sp)

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(stringResource(R.string.user_mock_age), fontSize = 12.sp, color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                Icon(Icons.Outlined.MoreVert, null)
            }

            Divider(modifier = Modifier.padding(vertical = 8.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                InfoRow(Icons.Outlined.Email, stringResource(R.string.user_mock_email))
                InfoRow(Icons.Outlined.Call, stringResource(R.string.user_mock_phone))
                InfoRow(Icons.Outlined.DateRange, stringResource(R.string.user_mock_reg_date))
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color(0xFF000000),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(text, fontSize = 12.sp, color = Color(0xFF6B7280))
    }
}

@Composable
fun Paginacion(
    paginaActual: Int,
    totalPaginas: Int,
    onPaginaChange: (Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedButton(
            onClick = { onPaginaChange(paginaActual - 1) },
            enabled = paginaActual > 1,
            border = BorderStroke(1.dp, Color.LightGray),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(stringResource(R.string.user_nav_prev), fontSize = 12.sp)
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
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
        ) {
            Text(stringResource(R.string.user_nav_next), fontSize = 12.sp)
        }
    }
}