package com.example.kotlinapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.kotlinapp.data.Item
import com.example.kotlinapp.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.kotlinapp.ui.item.ItemDetails
import com.example.kotlinapp.ui.item.toItemDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onEach

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModel(itemsRepository: ItemsRepository) : ViewModel() {

    private val _sortType = MutableStateFlow(SortBy.BY_OLDEST)
    private val _contacts = _sortType
        .flatMapLatest { sortType ->
            when(sortType) {
                SortBy.BY_OLDEST -> itemsRepository.getAllItemsStream().map { it -> it.map { it.toItemDetails() } }
                SortBy.BY_PRIORITY -> itemsRepository.getAllItemsByPriorityStream().map { it -> it.map { it.toItemDetails() } }
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(HomeUiState())
    val state = combine(_state, _sortType, _contacts) { state, sortType, contacts ->
        state.copy(
            itemList = contacts,
            sortType = sortType,

        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeUiState())

}

data class HomeUiState(
    val itemList: List<ItemDetails> = listOf(),
    val sortType: SortBy = SortBy.BY_OLDEST
)

enum class SortBy{
    BY_OLDEST,
    BY_PRIORITY
}