package ks.citiesapp.ui.screen.lists

import android.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ks.citiesapp.domain.CityList

@Composable
fun ListsScreen() {

    var showBottomSheet by remember { mutableStateOf(true) }

    // Пример данных — один список "Европа"
    val lists = listOf(
        CityList(
            id = "1",
            name = "Европа",
            fullName = "Список городов в Европе",
            color = Color.GREEN,
            cities = listOf("Париж", "Вена", "Берлин", "Варшава", "Милан")
        )
    )

    var selectedListIndex by remember { mutableStateOf(0) }

    if (showBottomSheet) {
        ListsBottomSheet(
            lists = lists,
            selectedListIndex = selectedListIndex,
            onListSelected = { index ->
                selectedListIndex = index
                // Здесь можно будет менять цвет и текст вкладки
            },
            onAddNewList = {
                showBottomSheet = false
                // Позже откроем форму добавления
            },
            onDismissRequest = {
                showBottomSheet = false
            }
        )
    } else {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Нажмите на вкладку, чтобы открыть список")
        }
    }
}