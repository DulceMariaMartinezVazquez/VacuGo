package com.example.vacugo.screens2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R

@Composable
fun LoginScreen(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onNavigateToRecovery: () -> Unit,
    onNavigateToSupport: () -> Unit,
    onLoginSuccess: () -> Unit,

    onNavigateToAdmin: () -> Unit,
    onNavigateToUser: () -> Unit,
    onNavigateToPersonal: () -> Unit

) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val errorRed = Color(0xFFD32F2F)
    val grayText = Color(0xFF666666)
    val lightBlue = Color(0xFFF0F7FF)

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            // Logo
            Box(
                modifier = Modifier
                    .size(180.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo_vacugo),
                    contentDescription = "Logo VacuGO",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.login_title),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(id = R.string.login_welcome),
                color = grayText,
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de Correo
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.email_label),
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    placeholder = { Text(stringResource(id = R.string.email_placeholder), color = Color.LightGray) },
                    leadingIcon = { Icon(Icons.Outlined.Mail, null, tint = Color.Gray) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color(0xFF1976D2)
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Campo de Contraseña
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = R.string.password_label), fontWeight = FontWeight.Medium, fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp).clickable { passwordVisible = !passwordVisible },
                        tint = Color.DarkGray
                    )
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Outlined.Lock, null, tint = Color.Gray) },
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = Color(0xFF1976D2)
                    )
                )
            }

            if (password.isNotEmpty() && password.length < 8) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.ErrorOutline, null, tint = errorRed, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(stringResource(id = R.string.password_error), color = errorRed, fontSize = 12.sp)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.forgot_password),
                color = Color(0xFF00A382),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.End).clickable { onNavigateToRecovery() }
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onLoginSuccess,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (email.isNotEmpty() && password.length >= 8) Color(0xFF1976D2) else Color(0xFFF8F9FA)
                ),
                enabled = email.isNotEmpty() && password.length >= 8
            ) {
                Text(stringResource(id = R.string.login_button), color = if (email.isNotEmpty() && password.length >= 8) Color.White else Color.LightGray, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Roles
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Vínculo para Paciente -> Va a UsuariosScreen
                Text(
                    text = stringResource(id = R.string.role_patient),
                    color = Color(0xFF007AFF),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onNavigateToUser() } // <--- Acción para Usuario
                )

                // Vínculo para Doctor -> Va a PersonalScreen
                Text(
                    text = stringResource(id = R.string.role_doctor),
                    color = Color(0xFF007AFF),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onNavigateToPersonal() } // <--- Acción para Personal/Doctor
                )

                // Vínculo para Administrador -> Va a HomeAdminScreen
                Text(
                    text = stringResource(id = R.string.role_admin),
                    color = Color(0xFF007AFF),
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onNavigateToAdmin() } // <--- Acción para Admin
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            HorizontalDivider(color = Color.LightGray)

            Spacer(modifier = Modifier.height(24.dp))

            // Botones Inferiores
            ActionCard(stringResource(id = R.string.create_account), Icons.Default.PersonAddAlt1, Color(0xFF00A382), onNavigateToRegister)
            Spacer(modifier = Modifier.height(12.dp))
            ActionCard(stringResource(id = R.string.support_center), Icons.AutoMirrored.Filled.HelpOutline, Color(0xFF007AFF), onNavigateToSupport)

            Spacer(modifier = Modifier.height(32.dp))

            // Idioma y Tema
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                ThemeCard(
                    text = stringResource(id = R.string.language_es),
                    icon = Icons.Default.Language,
                    modifier = Modifier.weight(1f),
                    onClick = { /* Idioma */ },
                    isDarkTheme = isDarkTheme
                )
                ThemeCard(
                    text = if (isDarkTheme) stringResource(id = R.string.mode_dark) else stringResource(id = R.string.mode_light),
                    icon = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                    modifier = Modifier.weight(1f),
                    onClick = onToggleTheme,
                    isDarkTheme = isDarkTheme
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ActionCard(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, iconColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().height(60.dp).clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.2.dp, Color(0xFFEEEEEE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface, fontSize = 16.sp)
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
        }
    }
}

@Composable
fun ThemeCard(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit, isDarkTheme: Boolean) {
    val lightBlue = Color(0xFFF0F7FF)
    Card(
        modifier = modifier.height(52.dp).clickable { onClick() },
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = if (isDarkTheme) Color(0xFF1E1E1E) else lightBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, null, tint = if (isDarkTheme) Color.White else Color(0xFF1A237E), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(10.dp))
            Text(text, color = if (isDarkTheme) Color.White else Color(0xFF1A237E), fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}


