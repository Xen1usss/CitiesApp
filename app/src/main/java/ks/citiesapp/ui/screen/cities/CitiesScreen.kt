package ks.citiesapp.ui.screen.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.City
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun CitiesScreen() {
    val cities = listOf(
        City("Париж", "III век до н.э."),
        City("Вена", "1147 год"),
        City("Берлин", "1237 год"),
        City("Варшава", "1321 год"),
        City("Милан", "1899 год")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(cities, key = { it.name }) { city ->
            CityItem(city = city)
        }
    }


}

@Composable
fun CityItem(city: City) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = city.name)
        Text(text = city.yearFounded)
    }
}