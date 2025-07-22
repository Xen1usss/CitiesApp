package ks.citiesapp.ui.screen.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.CityList


@Composable
fun ListsCarousel(
    lists: List<CityList>,
    selectedListIndex: Int,
    onListSelected: (Int) -> Unit,
    onAddNewList: () -> Unit,
    onListLongPressed: (Int) -> Unit
) {

    val circleSize = 64.dp

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        itemsIndexed(lists + null) { index, listOrNull ->
            if (listOrNull == null) {
                AddNewListCircle(onClick = onAddNewList, size = circleSize)
            } else {
                val isSelected = index == selectedListIndex

                ListCircleItem(
                    list = listOrNull,
                    isSelected = isSelected,
                    size = circleSize,
                    onClick = {
                        if (index < lists.size) {
                            onListSelected(index)
                        }
                    },
                    onLongClick = {
                        if (index < lists.size) {
                            onListLongPressed(index)
                        }
                    }
                )
            }
        }
    }

    if (selectedListIndex < lists.size) {
        Text(
            text = lists[selectedListIndex].fullName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}