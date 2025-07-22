package ks.citiesapp.ui.screen.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ks.citiesapp.data.repository.dao.CityListDao
import ks.citiesapp.domain.CityList
import ks.citiesapp.domain.entity.CityListEntity
import ks.citiesapp.domain.entity.toDomain
import java.util.UUID

class ListsViewModel(private val dao: CityListDao) : ViewModel() {

    private val _uiState = MutableStateFlow(ListsUiState())
    val uiState: StateFlow<ListsUiState> = _uiState.asStateFlow()

    init {
        loadLists()
    }

    private fun loadLists() {
        viewModelScope.launch {
            dao.getAll().collect { entities ->
                val lists = entities.map { it.toDomain() }
                _uiState.update { currentState ->
                    val newSelectedIndex = if (currentState.selectedListIndex in lists.indices)
                        currentState.selectedListIndex else 0
                    currentState.copy(
                        lists = lists,
                        selectedListIndex = newSelectedIndex,
                        selectedList = lists.getOrNull(newSelectedIndex)
                    )
                }
            }
        }
    }

//    private fun loadLists() {
//        viewModelScope.launch {
//            dao.getAll().collect { entities ->
//                val lists = entities.map { it.toDomain() }
//                _uiState.update { it.copy(lists = lists, selectedList = lists.firstOrNull()) }
//            }
//        }
//    }

    fun createNewList(name: String, fullName: String, color: Int, cities: List<String>) {
        viewModelScope.launch {
            val entity = CityListEntity(
                id = UUID.randomUUID().toString(),
                name = name,
                fullName = fullName,
                color = color,
                cities = cities.joinToString(",")
            )
            dao.insert(entity)
        }
    }

    fun selectList(index: Int) {
        _uiState.update { state ->
            val list = state.lists.getOrNull(index)
            state.copy(selectedListIndex = index, selectedList = list)
        }
    }
}

data class ListsUiState(
    val lists: List<CityList> = emptyList(),
    val selectedListIndex: Int = 0,
    val selectedList: CityList? = null
)