package ks.citiesapp.ui.screen.cities

import CityItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.City
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyListState
import org.burnoutcrew.reorderable.reorderable


@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun CitiesScreen(
    cities: List<City> = listOf()
) {
    var citiesState by remember { mutableStateOf(cities.toMutableList()) }

    val state = rememberReorderableLazyListState(onMove = { from, to ->
        citiesState = citiesState.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    })

    LazyColumn(
        state = state.listState,
        modifier = Modifier
            .fillMaxSize()
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(citiesState, key = { it.name }) { city ->
            ReorderableItem(state, key = city.name) { isDragging ->
                CityItem(
                    city = city,
                    modifier = Modifier
                        .animateItemPlacement()
                        .padding(vertical = 4.dp)
                )
            }
        }
    }
}
