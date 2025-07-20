package ks.citiesapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ks.citiesapp.ui.screen.main.MainScreen
import ks.citiesapp.ui.theme.CitiesAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CitiesAppTheme {
                MainScreen()
            }
        }
    }
}