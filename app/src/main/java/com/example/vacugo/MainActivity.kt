package com.example.vacugo


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.vacugo.ui.theme.AppEntry
import com.example.vacugo.ui.theme.VacuGoTheme
import androidx.navigation.compose.rememberNavController
import com.example.vacugo.navigation.NavGraph
import com.example.vacugo.screens5.HomeAdminScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VacuGoTheme{
                AppEntry()
            }

            //Dulce si ves esto, esto lo agg yo es mi main ajajajj
            val navController = rememberNavController()

            NavGraph(navController)
        }
    }
}