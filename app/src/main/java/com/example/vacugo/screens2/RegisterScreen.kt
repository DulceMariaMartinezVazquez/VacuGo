package com.ita.vacugologin.screens

import android.app.DatePickerDialog
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ita.vacugologin.R
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(onNavigateBack: () -> Unit) {
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    
    val primaryBlue = Color(0xFF3B6CB2)
    val grayText = Color(0xFF757575)
    val borderColor = Color(0xFFE0E0E0)

    var nombreCompleto by remember { mutableStateOf("") }
    var fechaNacimiento by remember { mutableStateOf("") }
    var curp by remember { mutableStateOf("") }
    var generoSeleccionado by remember { mutableStateOf("") }
    var perfilSeleccionado by remember { mutableStateOf("") }
    
    var isGenderExpanded by remember { mutableStateOf(false) }
    var isProfileExpanded by remember { mutableStateOf(false) }
    
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }

    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            fechaNacimiento = "$dayOfMonth/${month + 1}/$year"
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(
                                stringResource(R.string.register_title),
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
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
                HorizontalDivider(color = borderColor, thickness = 1.dp)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(130.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE0F7F9))
                    .clickable { launcher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(modifier = Modifier.size(100.dp).clip(CircleShape).background(Color(0xFFE8F8F9)))
                }
                
                Surface(
                    modifier = Modifier.align(Alignment.BottomEnd).padding(bottom = 8.dp, end = 8.dp).size(36.dp),
                    shape = CircleShape,
                    color = primaryBlue,
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.Default.PhotoCamera, contentDescription = stringResource(R.string.change_photo_content_desc), tint = Color.White, modifier = Modifier.size(18.dp))
                    }
                }
            }

            Text(
                text = stringResource(R.string.profile_photo),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.personal_info_section),
                modifier = Modifier.fillMaxWidth(),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = grayText
            )

            Spacer(modifier = Modifier.height(24.dp))

            FieldLabel(Icons.Outlined.Person, stringResource(R.string.full_name_label), primaryBlue)
            OutlinedTextField(
                value = nombreCompleto,
                onValueChange = { nombreCompleto = it },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                placeholder = { Text(stringResource(R.string.full_name_placeholder), color = Color.LightGray) },
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    FieldLabel(Icons.Outlined.CalendarMonth, stringResource(R.string.birth_date_label), primaryBlue)
                    OutlinedTextField(
                        value = fechaNacimiento,
                        onValueChange = { },
                        modifier = Modifier.padding(top = 8.dp).fillMaxWidth().clickable { datePickerDialog.show() },
                        enabled = false,
                        placeholder = { Text(stringResource(R.string.birth_date_placeholder), color = Color.LightGray) },
                        trailingIcon = { 
                            IconButton(onClick = { datePickerDialog.show() }) {
                                Icon(Icons.Default.CalendarMonth, null, tint = grayText)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            disabledTextColor = MaterialTheme.colorScheme.onSurface,
                            disabledBorderColor = borderColor,
                            disabledPlaceholderColor = Color.LightGray
                        )
                    )
                }
                Column(modifier = Modifier.weight(0.8f)) {
                    Text(text = stringResource(R.string.gender_label), fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Box(modifier = Modifier.padding(top = 8.dp)) {
                        OutlinedTextField(
                            value = if(generoSeleccionado.isEmpty()) stringResource(R.string.gender_select) else generoSeleccionado,
                            onValueChange = {},
                            modifier = Modifier.fillMaxWidth().clickable { isGenderExpanded = !isGenderExpanded },
                            readOnly = true,
                            enabled = false,
                            trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.Black) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                disabledTextColor = if(generoSeleccionado.isEmpty()) Color.LightGray else MaterialTheme.colorScheme.onSurface,
                                disabledBorderColor = borderColor
                            )
                        )
                        DropdownMenu(
                            expanded = isGenderExpanded,
                            onDismissRequest = { isGenderExpanded = false },
                            modifier = Modifier.fillMaxWidth(0.4f).background(Color.White)
                        ) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.gender_female)) },
                                onClick = { generoSeleccionado = context.getString(R.string.gender_female); isGenderExpanded = false }
                            )
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.gender_male)) },
                                onClick = { generoSeleccionado = context.getString(R.string.gender_male); isGenderExpanded = false }
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            FieldLabel(Icons.Outlined.Fingerprint, stringResource(R.string.curp_label), primaryBlue)
            OutlinedTextField(
                value = curp,
                onValueChange = { if (it.all { char -> char.isLetterOrDigit() }) curp = it.uppercase() },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                placeholder = { Text(stringResource(R.string.curp_placeholder), color = Color.LightGray) },
                shape = RoundedCornerShape(12.dp)
            )
            Text(
                stringResource(R.string.curp_helper),
                fontSize = 11.sp, color = grayText, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(stringResource(R.string.profile_type_section), modifier = Modifier.fillMaxWidth(), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = grayText)
            Box(modifier = Modifier.padding(top = 8.dp).fillMaxWidth()) {
                OutlinedTextField(
                    value = if(perfilSeleccionado.isEmpty()) stringResource(R.string.profile_type_placeholder) else perfilSeleccionado,
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth().clickable { isProfileExpanded = !isProfileExpanded },
                    readOnly = true,
                    enabled = false,
                    trailingIcon = { Icon(Icons.Default.KeyboardArrowDown, null, tint = Color.Black) },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = if(perfilSeleccionado.isEmpty()) Color.LightGray else MaterialTheme.colorScheme.onSurface,
                        disabledBorderColor = borderColor
                    )
                )
                DropdownMenu(
                    expanded = isProfileExpanded,
                    onDismissRequest = { isProfileExpanded = false },
                    modifier = Modifier.fillMaxWidth(0.8f).background(Color.White)
                ) {
                    listOf(R.string.role_patient, R.string.role_doctor, R.string.role_admin).forEach { resId ->
                        val label = stringResource(resId)
                        DropdownMenuItem(
                            text = { Text(label) },
                            onClick = { perfilSeleccionado = label; isProfileExpanded = false }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun FieldLabel(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
    }
}
