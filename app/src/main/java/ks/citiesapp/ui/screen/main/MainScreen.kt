package ks.citiesapp.ui.screen.main

import android.content.ClipData.Item
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun MainScreen() {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            NavigationBar {
                val items = listOf(
                    NavigationItem.Cities,
                    NavigationItem.Lists
                )
                items.forEach {
                    NavigationBarItem(
                        selected = true,
                        onClick = {},
                        icon = {
                            Icon(it.icon, contentDescription = null)
                        },
                        label = {
                            Text(text = stringResource(id = it.titleResId))
                        }
                    )
                }
            }
        }
    ) {

    }
}