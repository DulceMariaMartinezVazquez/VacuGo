package com.example.vacugo.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource // IMPORTANTE
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vacugo.R
import androidx.compose.ui.graphics.graphicsLayer


@Composable
fun WelcomeScreen(onNextClick: () -> Unit, onLoginClick: () -> Unit) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .navigationBarsPadding()
            .verticalScroll(scrollState)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 24.dp, bottom = 12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Home,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        HorizontalDivider(thickness = 1.dp, color = Color(0xFFF0F0F0))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE3F2FD)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.welcome),
                    contentDescription = null,

                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                        .graphicsLayer(
                            scaleX = 1.5f,
                            scaleY = 1.7f
                        )
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.welcome_to))
                    withStyle(style = SpanStyle(color = Color(0xFF34A3F7))) {
                        append(" ")
                        append(stringResource(id = R.string.app_names))
                        append(" ")
                    }
                },
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(id = R.string.welcome_description),
                textAlign = TextAlign.Center,
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 30.dp)
            )

            FeatureRow(
                icon = Icons.Outlined.Email,
                title = stringResource(id = R.string.feature_expediente_title),
                description = stringResource(id = R.string.feature_expediente_desc)
            )
            Spacer(modifier = Modifier.height(20.dp))
            FeatureRow(
                icon = Icons.Outlined.Person,
                title = stringResource(id = R.string.feature_fila_title),
                description = stringResource(id = R.string.feature_fila_desc)
            )
            Spacer(modifier = Modifier.height(20.dp))
            FeatureRow(
                icon = Icons.Outlined.AccountBox,
                title = stringResource(id = R.string.feature_historial_title),
                description = stringResource(id = R.string.feature_historial_desc)
            )

            Spacer(modifier = Modifier.height(150.dp))

            Button(
                onClick = onNextClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A3F7))
            ) {
                Text(text = stringResource(id = R.string.btn_next), fontSize = 16.sp, color = Color.White)
            }

            TextButton(
                onClick = onLoginClick,
                modifier = Modifier.padding(top = 8.dp, bottom = 40.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(id = R.string.already_have_account))
                        withStyle(style = SpanStyle(color = Color(0xFF34A3F7), fontWeight = FontWeight.Bold)) {
                            append(stringResource(id = R.string.login_action))
                        }
                    },
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun FeatureRow(icon: ImageVector, title: String, description: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFEFF8FF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF34A3F7),
                modifier = Modifier.size(26.dp)
            )
        }

        Column(modifier = Modifier.padding(start = 20.dp)) {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color(0xFF333333))
            Text(text = description, fontSize = 14.sp, color = Color.Gray, lineHeight = 18.sp)
        }
    }
}