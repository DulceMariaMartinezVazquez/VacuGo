package com.example.vacugo.screens2

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerifyCodeScreen(
    onNavigateToNewPassword: () -> Unit,
    onNavigateBack: () -> Unit
) {
    val accentBlue = Color(0xFF007AFF)
    val grayText = Color(0xFF666666)
    val lightGray = Color(0xFF999999)
    val softBlue = Color(0xFFA6C4FF)

    val codeStates = remember { mutableStateListOf("", "", "", "", "", "") }
    
    var ticks by remember { mutableIntStateOf(30) }
    LaunchedEffect(Unit) {
        while(ticks > 0) {
            delay(1000)
            ticks--
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(Color.White), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Surface(
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = Color(0xFFF0F7FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Email, null, modifier = Modifier.size(32.dp), tint = accentBlue)
                }
            }

            Text(
                text = stringResource(R.string.verify_email_title),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 32.dp)
            )

            val subtitleText = buildAnnotatedString {
                append(stringResource(R.string.verify_email_desc))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Black)) {
                    append("usuario@ejemplo.com")
                }
            }

            Text(
                text = subtitleText,
                textAlign = TextAlign.Center,
                color = grayText,
                fontSize = 16.sp,
                lineHeight = 22.sp,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                codeStates.forEachIndexed { index, value ->
                    CodeBox(
                        text = value,
                        isFocused = value.isEmpty() && (index == 0 || codeStates[index-1].isNotEmpty()),
                        modifier = Modifier.weight(1f),
                        onValueChange = { newValue ->
                            if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                                codeStates[index] = newValue
                            }
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Timer, null, modifier = Modifier.size(18.dp), tint = lightGray)
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = if (ticks > 0) stringResource(R.string.resend_prefix) + ticks.toString().padStart(2, '0') else stringResource(R.string.resend_code_button),
                    color = lightGray,
                    fontSize = 15.sp,
                    modifier = Modifier.clickable(enabled = ticks == 0) { ticks = 30 }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.change_email_link),
                color = accentBlue,
                fontWeight = FontWeight.Medium,
                fontSize = 15.sp,
                modifier = Modifier.clickable { onNavigateBack() }
            )

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onNavigateToNewPassword,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (codeStates.all { it.isNotEmpty() }) accentBlue else softBlue
                ),
                enabled = codeStates.all { it.isNotEmpty() }
            ) {
                Text(stringResource(R.string.verify_code_button), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = stringResource(R.string.no_code_received_footer),
                fontSize = 12.sp,
                color = lightGray,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp).padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun CodeBox(text: String, isFocused: Boolean, onValueChange: (String) -> Unit, modifier: Modifier = Modifier) {
    OutlinedTextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier.aspectRatio(0.85f),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 20.sp, fontWeight = FontWeight.Bold),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFF007AFF),
            unfocusedBorderColor = Color(0xFFE0E0E0)
        ),
        singleLine = true
    )
}
