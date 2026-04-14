package com.example.vacugo.screen

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.outlined.Info
import com.example.vacugo.R

@Composable
fun ConfigScreen(onStartClick: () -> Unit) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            openAppSettings(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp, end = 24.dp, bottom = 12.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(
                    text = stringResource(id = R.string.config_title),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.size(18.dp))
            }
        }

        HorizontalDivider(thickness = 1.dp, color = Color(0xFFF0F0F0))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(text = stringResource(id = R.string.config_experience_title), fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = buildAnnotatedString {
                    append(stringResource(id = R.string.config_description_part1))
                    append(" ")
                    withStyle(style = SpanStyle(color = Color(0xFF34A3F7), fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.app_names))
                    }
                    append(" ")
                    append(stringResource(id = R.string.config_description_part2))
                },
                fontSize = 14.sp, color = Color.Gray, lineHeight = 20.sp
            )

            Spacer(modifier = Modifier.height(28.dp))

            PermissionCard(
                icon = Icons.Outlined.Notifications,
                title = stringResource(id = R.string.perm_notifications_title),
                description = stringResource(id = R.string.perm_notifications_desc),
                accentColor = Color(0xFF34A3F7),
                onClick = { permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS) }
            )

            PermissionCard(
                icon = Icons.Outlined.Place,
                title = stringResource(id = R.string.perm_location_title),
                description = stringResource(id = R.string.perm_location_desc),
                accentColor = Color(0xFF4CAF50),
                onClick = { permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
            )

            PermissionCard(
                icon = Icons.Outlined.Call,
                title = stringResource(id = R.string.perm_camera_title),
                description = stringResource(id = R.string.perm_camera_desc),
                accentColor = Color(0xFFE91E63),
                onClick = { permissionLauncher.launch(Manifest.permission.CAMERA) }
            )
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFF7F9FA))
                    .padding(16.dp)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = stringResource(id = R.string.config_info_box),
                        fontSize = 13.sp,
                        color = Color.Gray,
                        lineHeight = 18.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))


            Button(
                onClick = onStartClick,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF34A3F7))
            ) {
                Text(text = stringResource(id = R.string.btn_start_now), fontWeight = FontWeight.Bold, color = Color.White)
            }

            TextButton(
                onClick = { /* Omitir */ },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 40.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_configure_later), color = Color.Gray)
            }
        }
    }
}

@Composable
fun PermissionCard(icon: ImageVector, title: String, description: String, accentColor: Color, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .border(1.dp, Color(0xFFEFEFEF), RoundedCornerShape(16.dp))
            .background(Color.White, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(accentColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(22.dp))
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = description, fontSize = 12.sp, color = Color.Gray)
            }

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF0F0F0), contentColor = Color.Black),
                shape = CircleShape,
                modifier = Modifier.height(30.dp),
                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
            ) {
                Text(text = stringResource(id = R.string.btn_allow), fontSize = 12.sp)
            }
        }
    }
}

fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}