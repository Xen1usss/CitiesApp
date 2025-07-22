package ks.citiesapp.ui.screen.cities

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.City

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CityItem(
    city: City,
    modifier: Modifier = Modifier,
    onLongPress: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {},
                onLongClick = {
                    onLongPress?.invoke()
                }
            )
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = city.name)
        Text(text = city.yearFounded)
    }
}