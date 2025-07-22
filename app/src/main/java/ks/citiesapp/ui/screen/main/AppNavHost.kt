package ks.citiesapp.ui.screen.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ks.citiesapp.data.repository.database.AppDatabase
import ks.citiesapp.domain.City
import ks.citiesapp.ui.screen.cities.CitiesScreen
import ks.citiesapp.ui.screen.lists.ListsScreen
import ks.citiesapp.ui.screen.lists.ListsViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: ListsViewModel,
    database: AppDatabase,
    modifier: Modifier = Modifier,
    startDestination: String = "cities"
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable("cities") {
            val uiState by viewModel.uiState.collectAsState()
            val selectedList = uiState.selectedList

            if (selectedList != null) {
                // Преобразуем имена городов в объекты City с годами
                val cities = selectedList.cities.map { cityName ->
                    City(cityName, getYearForCity(cityName))
                }
                CitiesScreen(cities = cities)
            } else {
                // Запасной вариант
                CitiesScreen(
                    cities = listOf(
                        City("Париж", "III век до н.э."),
                        City("Вена", "1147 год")
                    )
                )
            }
        }
        composable("lists") {
            ListsScreen(
                database = database, // ← Передаём базу
                sharedViewModel = viewModel, // ← Передаём общий ViewModel
                navController = navController
            )
        }
    }
}

private fun getYearForCity(name: String): String {
    return when (name) {
        "Париж" -> "III век до н.э."
        "Вена" -> "1147 год"
        "Берлин" -> "1237 год"
        "Варшава" -> "1321 год"
        "Милан" -> "1899 год"
        "Москва" -> "1147 год"
        "Хабаровск" -> "1858 год"
        "Ираклион" -> "Древность"
        "Афины" -> "VIII век до н.э."
        "Мюнхен" -> "1158 год"
        "Линц" -> "1274 год"
        "Зальцбург" -> "799 год"
        else -> "Неизвестно"
    }
}