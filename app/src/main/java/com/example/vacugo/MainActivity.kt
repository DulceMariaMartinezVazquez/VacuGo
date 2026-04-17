package com.example.vacugo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import com.example.vacugo.ui.theme.VacuGoTheme
import com.example.vacugo.ui.theme.AppEntry

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {

            var isDarkTheme by remember {
                mutableStateOf(false)
            }

            VacuGoTheme(darkTheme = isDarkTheme) {

                AppEntry(
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = {
                        isDarkTheme = !isDarkTheme
                    }
                )

            }

        }

    }

}