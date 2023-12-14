package com.example.kotlinapp

import android.app.Application
import com.example.kotlinapp.data.AppContainer
import com.example.kotlinapp.data.AppDataContainer

class ItemApplication : Application(){

    lateinit var container: AppContainer;

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}