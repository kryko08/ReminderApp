package com.example.kotlinapp.data

import kotlinx.coroutines.flow.Flow

class OfflineItemRepository(private val itemDao: ItemDao): ItemsRepository{
    override fun getAllItemsByPriorityStream(): Flow<List<Item>> = itemDao.getAllItemsByPriority()

    override fun getAllItemsStream(): Flow<List<Item>> = itemDao.getAllItems()

    override fun getItemByIdStream(id: Int): Flow<Item> = itemDao.getItemById(id)

    override suspend fun insertItem(item: Item) = itemDao.insert(item)

    override suspend fun deleteItem(item: Item) = itemDao.delete(item)

    override suspend fun updateItem(item: Item) = itemDao.update(item)

}