package ks.citiesapp.ui.screen.lists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ks.citiesapp.domain.CityList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsBottomSheet(
    lists: List<CityList>,
    selectedListIndex: Int,
    onListSelected: (Int) -> Unit,
    onAddNewList: () -> Unit,
    onDismissRequest: () -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { true }
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            ListsCarousel(
                lists = lists,
                selectedListIndex = selectedListIndex,
                onListSelected = onListSelected,
                onAddNewList = onAddNewList
            )
        }
    }

}