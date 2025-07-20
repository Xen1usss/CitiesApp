package ks.citiesapp.ui.screen.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ks.citiesapp.ui.screen.cities.CitiesScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "cities"
    ) {
        composable("cities") {
            CitiesScreen()
        }
        composable("lists") {
            // Пока ничего не делаем
        }
    }
}