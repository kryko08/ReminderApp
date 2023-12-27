package com.example.kotlinapp.ui.home

import androidx.lifecycle.ViewModel
import com.example.kotlinapp.data.Item
import com.example.kotlinapp.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope

class HomeViewModel(itemsRepository: ItemsRepository) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> =
        itemsRepository.getAllItemsStream().map { HomeUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = HomeUiState()
            )
}

data class HomeUiState(val itemList: List<Item> = listOf())