package ks.citiesapp.ui.screen.lists

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.CityList
import kotlin.math.abs

@Composable
fun ListsCarousel(
    lists: List<CityList>,
    onListSelected: (Int) -> Unit,
    onAddNewList: () -> Unit,
    onListLongPressed: (Int) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val density = LocalDensity.current
    val screenWidthPx = with(density) {
        LocalConfiguration.current.screenWidthDp.dp.toPx()
    }

    val fullList = listOf<CityList?>(null) + lists
    val itemSize = 64.dp
    val itemSizePx = with(density) { itemSize.toPx() }
    val horizontalPadding = (screenWidthPx - itemSizePx) / 2f
    val contentPadding = PaddingValues(horizontal = with(density) { horizontalPadding.toDp() })

    // === НОВАЯ ЛОГИКА ===
    var targetIndex by remember { mutableStateOf<Int?>(null) }
    var isAutoScrolling by remember { mutableStateOf(false) }

    val centerItemIndex by remember {
        derivedStateOf {
            if (isAutoScrolling) return@derivedStateOf targetIndex

            val center = screenWidthPx / 2f
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo

            visibleItems
                .minByOrNull { item ->
                    val itemCenter = item.offset + item.size / 2
                    abs(itemCenter - center)
                }?.index
        }
    }

    LaunchedEffect(lazyListState.isScrollInProgress) {
        if (!lazyListState.isScrollInProgress && !isAutoScrolling) {
            val center = screenWidthPx / 2f
            val visibleItems = lazyListState.layoutInfo.visibleItemsInfo

            if (visibleItems.isNotEmpty()) {
                val closestItem = visibleItems.minByOrNull { item ->
                    val itemCenter = item.offset + item.size / 2
                    abs(itemCenter - center)
                }

                closestItem?.let { item ->
                    targetIndex = item.index
                    isAutoScrolling = true
                    lazyListState.animateScrollToItem(item.index)
                }
            }
        }
    }

    LaunchedEffect(lazyListState.firstVisibleItemIndex) {
        if (isAutoScrolling) {
            kotlinx.coroutines.delay(100)
            isAutoScrolling = false
        }
    }

    Box {
        LazyRow(
            state = lazyListState,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = contentPadding,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
        ) {
            itemsIndexed(fullList) { index, item ->
                val layoutInfo = lazyListState.layoutInfo
                val itemCenter = layoutInfo.visibleItemsInfo
                    .find { it.index == index }
                    ?.let { it.offset + it.size / 2 }

                val distanceFromCenter = if (itemCenter != null)
                    abs(itemCenter - screenWidthPx / 2f)
                else
                    Float.MAX_VALUE

                val scale = 1f - (distanceFromCenter / (screenWidthPx / 2f)).coerceIn(0f, 1f) * 0.3f
                val animatedScale by animateFloatAsState(scale, label = "scale")

                Box(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = animatedScale
                            scaleY = animatedScale
                        }
                ) {
                    when {
                        index == 0 && item == null -> {
                            Box(modifier = Modifier.size(64.dp)) {
                                AddNewListCircle(onClick = onAddNewList, size = 64.dp)
                            }
                        }
                        item != null -> {
                            val selected = centerItemIndex == index
                            ListCircleItem(
                                list = item,
                                isSelected = selected,
                                size = 64.dp,
                                onClick = {
                                    onListSelected(index - 1)
                                },
                                onLongClick = {
                                    onListLongPressed(index - 1)
                                }
                            )
                        }
                        else -> {
                            Box(modifier = Modifier.size(64.dp))
                        }
                    }
                }
            }
        }

        val selectedListIndex = centerItemIndex?.takeIf { it > 0 }?.minus(1)
        if (selectedListIndex != null && selectedListIndex in lists.indices) {
            Text(
                text = lists[selectedListIndex].fullName,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}