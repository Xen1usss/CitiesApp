package ks.citiesapp.ui.screen.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector
import ks.citiesapp.R

sealed class NavigationItem(
    val titleResId: Int,
    val icon: ImageVector
) {
    object Cities: NavigationItem(
        titleResId = R.string.navigation_item_main,
        icon = Icons.Filled.Menu
    )

    object Lists: NavigationItem(
        titleResId = R.string.navigation_item_lists,
        icon = Icons.Filled.Build
    )
}