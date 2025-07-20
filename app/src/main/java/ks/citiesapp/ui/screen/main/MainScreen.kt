package ks.citiesapp.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ks.citiesapp.ui.screen.cities.CitiesScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        NavigationItem.Cities,
        NavigationItem.Lists
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar() {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

//                val selectedItemPosition = remember {
//                    mutableStateOf(0)
//                }

//                items.forEachIndexed() { index, item ->
//                    NavigationBarItem(
//                        selected = selectedItemPosition.value == index,
//                        onClick = { selectedItemPosition.value = index},
//                        icon = {
//                            Icon(item.icon, contentDescription = null)
//                        },
//                        label = {
//                            Text(text = stringResource(id = item.titleResId))
//                        },
//                    )
//                }

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination == item.route,
                        onClick = {
                            if (currentDestination != item.route) {
                                navController.navigate(item.route) {
                                    // Чистим стек до старта, чтобы избежать дублирования
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(imageVector = item.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = item.titleResId))
                        }
                    )
                }
            }
        }

    ) {     paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavigationItem.Cities.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavigationItem.Cities.route) {
                CitiesScreen()
            }
            composable(NavigationItem.Lists.route) {
                // Пока ничего не делаем
                // Можно оставить пустым или вставить заглушку:
                // Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                //     Text("Раздел в разработке")
                // }
            }
        }

    }
}