package com.example.kotlinapp.data

import kotlinx.coroutines.flow.Flow


// Repository provides insert, update, delete, and retrieve of [Item] from a given data source.

interface ItemsRepository {
    fun getAllItemsByPriorityStream() : Flow<List<Item>>
    fun getAllItemsStream() : Flow<List<Item>>
    fun getItemByIdStream(id: Int) : Flow<Item>
    suspend fun insertItem(item: Item)
    suspend fun deleteItem(item: Item)
    suspend fun updateItem(item: Item)
}