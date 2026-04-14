package com.ita.vacugologin.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ita.vacugologin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SupportScreen(onNavigateBack: () -> Unit) {
    val scrollState = rememberScrollState()
    val primaryBlue = Color(0xFF3B6CB2)
    val grayText = Color(0xFF666666)

    var expandedIndex by remember { mutableIntStateOf(-1) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Column(modifier = Modifier.padding(start = 8.dp)) {
                            Text(stringResource(R.string.support_title), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(stringResource(R.string.support_subtitle), fontSize = 14.sp, color = grayText)
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back_content_desc))
                        }
                    },
                    actions = {
                        Surface(
                            shape = CircleShape,
                            color = primaryBlue,
                            modifier = Modifier.padding(end = 16.dp).size(40.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Default.QuestionMark, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
                )
                HorizontalDivider(color = Color(0xFFEEEEEE), thickness = 1.dp)
            }
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    stringResource(R.string.faq_title),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                val faqs = listOf(
                    Triple(R.string.faq_turnos_cat, R.string.faq_turnos_q, R.string.faq_turnos_a),
                    Triple(R.string.faq_expedientes_cat, R.string.faq_expedientes_q, R.string.faq_expedientes_a),
                    Triple(R.string.faq_seguridad_cat, R.string.faq_seguridad_q, R.string.faq_seguridad_a),
                    Triple(R.string.faq_salud_cat, R.string.faq_salud_q, R.string.faq_salud_a)
                )

                faqs.forEachIndexed { index, faq ->
                    FaqItem(
                        category = stringResource(faq.first),
                        question = stringResource(faq.second),
                        answer = stringResource(faq.third),
                        isOpen = expandedIndex == index,
                        onToggle = { expandedIndex = if (expandedIndex == index) -1 else index },
                        grayTextColor = grayText
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                Text(stringResource(R.string.not_found_support), fontSize = 15.sp, color = grayText)
                Text(stringResource(R.string.contact_us_directly), fontWeight = FontWeight.Bold, fontSize = 18.sp)

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = primaryBlue)
                ) {
                    Icon(Icons.Default.Email, null, tint = Color.White)
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(stringResource(R.string.send_support_email), fontWeight = FontWeight.Bold)
                }
                
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}

@Composable
fun FaqItem(category: String, question: String, answer: String, isOpen: Boolean, onToggle: () -> Unit, grayTextColor: Color) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable { onToggle() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, Color(0xFFEEEEEE))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(category, color = Color(0xFF3B6CB2), fontWeight = FontWeight.Bold, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(question, fontWeight = FontWeight.Bold, fontSize = 17.sp, modifier = Modifier.weight(1f), lineHeight = 22.sp)
                Icon(
                    imageVector = if (isOpen) Icons.Default.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            if (isOpen) {
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = Color(0xFFF5F5F5))
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth()) {
                   Icon(Icons.Outlined.Info, null, tint = Color(0xFF007AFF), modifier = Modifier.size(20.dp))
                   Spacer(modifier = Modifier.width(12.dp))
                   Text(answer, fontSize = 14.sp, color = grayTextColor, lineHeight = 20.sp)
                }
            }
        }
    }
}
