package com.example.kotlinapp.ui

import android.text.Spannable.Factory
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.kotlinapp.ItemApplication
import com.example.kotlinapp.ui.home.HomeViewModel
import com.example.kotlinapp.ui.item.ItemDetailViewModel
import com.example.kotlinapp.ui.item.ItemEntryViewModel

object AppViewModelProvider{
    val Factory = viewModelFactory {
        initializer {
            ItemEntryViewModel(itemApplication().container.itemsRepository)
        }
        initializer {
            HomeViewModel(itemApplication().container.itemsRepository)
        }
        initializer {
            ItemDetailViewModel(this.createSavedStateHandle(),
            itemApplication().container.itemsRepository)
        }
    }
}

fun CreationExtras.itemApplication(): ItemApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ItemApplication)