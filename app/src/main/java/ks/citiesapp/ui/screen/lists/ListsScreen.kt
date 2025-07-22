package ks.citiesapp.ui.screen.lists

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ks.citiesapp.data.CitiesRepository
import ks.citiesapp.data.repository.database.AppDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    database: AppDatabase,
    sharedViewModel: ListsViewModel? = null,
    navController: NavController
) {

    val viewModel = sharedViewModel ?: viewModel { ListsViewModel(database.cityListDao()) }

    val uiState by viewModel.uiState.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }

    val repository = CitiesRepository()
    val allCities = repository.getAllCities()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { true }
    )

    LaunchedEffect(Unit) {
        sheetState.show()
    }

    if (sheetState.currentValue != SheetValue.Hidden) {
        ListsBottomSheet(
            lists = uiState.lists,
            selectedListIndex = uiState.selectedListIndex,
            onListSelected = { index ->
                viewModel.selectList(index)
                navController.navigate("cities")
            },
            onAddNewList = { showCreateDialog = true },
            onDismissRequest = { },
            sheetState = sheetState
        )
    }

    if (showCreateDialog) {
        CreateListScreen(
            onConfirm = { name, fullName, color, cities ->
                viewModel.createNewList(name, fullName, color, cities)
                showCreateDialog = false
            },
            onDismiss = { showCreateDialog = false },
            availableCities = allCities
        )
    }
}