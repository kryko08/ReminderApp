package com.example.kotlinapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "items")
data class Item(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val header: String,
    val description: String,
    val priority: Int,
)
