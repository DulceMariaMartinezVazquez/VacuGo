package com.example.vacugo.screens2

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPasswordScreen(
    onPasswordReset: () -> Unit,
    onNavigateBack: () -> Unit
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val lightGray = Color(0xFF999999)
    val accentBlue = Color(0xFF007AFF)

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                stringResource(R.string.new_password_title),
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(end = 48.dp)
                            )
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_content_desc))
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.security_setup_title),
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = stringResource(R.string.security_setup_desc),
                color = lightGray,
                fontSize = 15.sp,
                lineHeight = 20.sp,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = stringResource(R.string.new_password_label), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                placeholder = { Text("••••••••", color = Color.LightGray) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))
            
            RequirementItem(stringResource(R.string.req_min_chars), password.length >= 8)
            RequirementItem(stringResource(R.string.req_uppercase), password.any { it.isUpperCase() })
            RequirementItem(stringResource(R.string.req_number), password.any { it.isDigit() })

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = stringResource(R.string.confirm_password_label), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                placeholder = { Text("••••••••", color = Color.LightGray) },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(
                            imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(48.dp))

            val isEnabled = password.length >= 8 && 
                            password.any { it.isUpperCase() } && 
                            password.any { it.isDigit() } && 
                            password == confirmPassword

            Button(
                onClick = onPasswordReset,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isEnabled) accentBlue else Color(0xFFF8F9FA)
                ),
                enabled = isEnabled
            ) {
                Text(
                    stringResource(R.string.reset_password_button), 
                    color = if (isEnabled) Color.White else Color.LightGray, 
                    fontWeight = FontWeight.Bold, 
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.back_to_login_short),
                color = accentBlue,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNavigateBack() },
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            Text(
                stringResource(R.string.security_footer_note),
                fontSize = 12.sp,
                color = lightGray,
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                textAlign = TextAlign.Center,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun RequirementItem(text: String, isMet: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically, 
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Surface(
            modifier = Modifier.size(18.dp),
            shape = CircleShape,
            border = BorderStroke(1.dp, if (isMet) Color(0xFF66BB6A) else Color.LightGray),
            color = if (isMet) Color(0xFF66BB6A) else Color.White
        ) {
            if (isMet) {
                Icon(Icons.Default.Check, null, tint = Color.White, modifier = Modifier.padding(2.dp))
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = text, fontSize = 14.sp, color = if (isMet) Color.Black else Color.Gray)
    }
}
