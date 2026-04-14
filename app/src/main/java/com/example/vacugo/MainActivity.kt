package com.example.vacugo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vacugo.ui.theme.AppEntry
import com.example.vacugo.ui.theme.VacuGoTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VacuGoTheme{
                AppEntry()
            }
        }
    }
}