package ks.citiesapp.ui.screen.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ks.citiesapp.data.repository.database.AppDatabase
import ks.citiesapp.ui.screen.lists.ListsViewModel


@Composable
fun MainScreen(
    database: AppDatabase
) {
    val navController = rememberNavController()
    val viewModel: ListsViewModel = viewModel { ListsViewModel(database.cityListDao()) }
    val uiState by viewModel.uiState.collectAsState()

    // Получаем текущий маршрут ДО Scaffold
    val currentDestination by navController.currentBackStackEntryAsState()
    val currentRoute = currentDestination?.destination?.route

    // Цвет и имя для вкладки "Списки"
    val displayColor = uiState.selectedList?.color?.let { Color(it) }
        ?: MaterialTheme.colorScheme.primary
    val displayLabel = uiState.selectedList?.name ?: "Списки"

    Scaffold(
        bottomBar = {
            NavigationBar {
                val items = listOf(NavigationItem.Cities, NavigationItem.Lists)
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            if (item == NavigationItem.Lists) {
                                CircleIcon(color = displayColor, size = 24)
                            } else {
                                Icon(imageVector = item.icon, contentDescription = null)
                            }
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        }
                    )
                }
            }
        }
    ) { padding ->
        // ✅ ВАЖНО: NavHost создаёт граф навигации
        AppNavHost(
            navController = navController,
            viewModel = viewModel,
            database = database,
            modifier = Modifier.padding(padding)
        )
    }
}