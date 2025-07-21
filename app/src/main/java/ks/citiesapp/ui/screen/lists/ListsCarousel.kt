package ks.citiesapp.ui.screen.lists

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.CityList
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue

@Composable
fun ListsCarousel(
    lists: List<CityList>,
    selectedListIndex: Int,
    onListSelected: (Int) -> Unit,
    onAddNewList: () -> Unit
) {
    val pagerState = rememberPagerState(
        pageCount = { lists.size + 1 },
        initialPage = selectedListIndex)

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            if (page < lists.size) {
                onListSelected(page)
            }
        }
    }

    Column {
        // Карусель
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
        ) { page ->
            if (page == lists.size) {
                // Кнопка '+'
                AddNewListButton(onAddNewList)
            } else {
                ListCard(lists[page])
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Вручную анимированный индикатор
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
        ) {
            val tabWidth = 120.dp
            val gap = 16.dp
            val itemWidthWithGap = tabWidth + gap

            val targetIndex = pagerState.currentPage.coerceAtMost(lists.size - 1) // исключаем '+'

            val targetOffset by animateDpAsState(
                targetValue = itemWidthWithGap * targetIndex + (tabWidth / 2) - (itemWidthWithGap / 2),
                label = "indicatorOffset"
            )

            Box(
                modifier = Modifier
                    .offset(x = targetOffset)
                    .width(tabWidth)
                    .height(4.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        Text(
            text = "Полное имя списка: ${
                if (pagerState.currentPage < lists.size) 
                    lists[pagerState.currentPage].fullName 
                else "Новый список"
            }",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun ListCard(list: CityList) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(androidx.compose.ui.graphics.Color(list.color)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = list.name,
                color = androidx.compose.ui.graphics.Color.White,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

// Функция для кнопки '+'
@Composable
fun AddNewListButton(onAddNewList: () -> Unit) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .width(120.dp),
        onClick = onAddNewList
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "+",
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}