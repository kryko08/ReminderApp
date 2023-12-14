package com.example.kotlinapp.data

import android.content.Context


interface AppContainer {
    val itemsRepository: ItemsRepository
}

class AppDataContainer(private val context: Context): AppContainer {
    override val itemsRepository: ItemsRepository by lazy {
        OfflineItemRepository(ItemDatabase.getDatabase(context).itemDao())
    }
}