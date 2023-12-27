package com.example.kotlinapp.ui.item

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.toUpperCase
import androidx.lifecycle.ViewModel
import com.example.kotlinapp.data.Item
import com.example.kotlinapp.data.ItemsRepository

class ItemEntryViewModel(private val itemsRepository: ItemsRepository): ViewModel() {

    var itemUiState by mutableStateOf(ItemUiState())
        private set

    fun updateUiState(itemDetails: ItemDetails){
        itemUiState = ItemUiState(itemDetails = itemDetails, isEntryValid = validateInput(itemDetails))
    }

    suspend fun saveItem(){
        if (validateInput()){
            itemsRepository.insertItem(itemUiState.itemDetails.toItem())
        }
    }

    private fun validateInput(uiState: ItemDetails = itemUiState.itemDetails): Boolean{
        if (uiState.description.isNotBlank() && uiState.priority.isNotBlank() && uiState.header.isNotBlank()){
            return true
        }
        return false
    }

}

data class ItemUiState(
    val itemDetails: ItemDetails = ItemDetails(),
    val isEntryValid: Boolean = false
)

data class ItemDetails(
    val id: Int = 0,
    val header: String = "",
    val description: String = "",
    val priority: String = "",
)

fun ItemDetails.toItem(): Item = Item(
    id = id,
    header = header,
    description = description,
    // priority = priority.toIntOrNull() ?: 0,
    priority = PriorityCategory.valueOf(priority.uppercase()).ordinal
)

fun Item.toItemUiState(isEntryValid: Boolean): ItemUiState = ItemUiState(
    itemDetails = this.toItemDetails(),
    isEntryValid = isEntryValid
)

fun Item.toItemDetails(): ItemDetails = ItemDetails(
    id = id,
    header = header,
    description = description,
    // priority = priority.toString()
    priority = PriorityCategory.values()[priority].toString()
)

enum class PriorityCategory(val priority: String){
    LOW("Low"),
    MEDIUM("Medium"),
    HIGH("High")
}