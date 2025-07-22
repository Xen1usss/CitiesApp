package ks.citiesapp.ui.screen.cities

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.City
import org.burnoutcrew.reorderable.*


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

@Composable
fun CityItem(city: City, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = city.name)
        Text(text = city.yearFounded)
    }
}