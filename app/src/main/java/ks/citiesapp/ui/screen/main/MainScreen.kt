package ks.citiesapp.ui.screen.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ks.citiesapp.ui.screen.cities.CitiesScreen
import ks.citiesapp.ui.screen.lists.ListsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val items = listOf(
        NavigationItem.Cities,
        NavigationItem.Lists
    )

    val defaultCircleColor = MaterialTheme.colorScheme.primary

    val initialColor = MaterialTheme.colorScheme.primary
    var circleColor by remember { mutableStateOf(initialColor) }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar() {
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination == item.route,
                        onClick = {
                            if (currentDestination != item.route) {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            if (item == NavigationItem.Lists) {
                                CircleIcon(color = circleColor, size = 24)
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
                ListsScreen()
            }
        }

    }
}