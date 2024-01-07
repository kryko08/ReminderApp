package com.example.kotlinapp.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kotlinapp.data.Item
import com.example.kotlinapp.data.ItemsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ItemDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val itemsRepository: ItemsRepository
): ViewModel() {

    private val itemId: Int = checkNotNull(savedStateHandle[ItemDetailsDestination.itemIdKey])
    val uiState: StateFlow<ItemDetailUiState> = itemsRepository.getItemByIdStream(itemId)
        .filterNotNull()
        .map {
            ItemDetailUiState(itemDetails = it.toItemDetails())
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = ItemDetailUiState()
        )

    suspend fun deleteItem() {
        itemsRepository.deleteItem(uiState.value.itemDetails.toItem())
    }
}

data class ItemDetailUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEdited: Boolean = false,
    val isValid: Boolean = true
)


