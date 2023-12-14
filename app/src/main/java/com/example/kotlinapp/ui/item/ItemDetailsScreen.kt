package com.example.kotlinapp.ui.item

import com.example.kotlinapp.R
import com.example.kotlinapp.ui.navigation.NavigationDestination

object ItemDetailsDestination: NavigationDestination {
    override val route = "item_details"
    override val title = R.string.item_details_title
    const val itemIdKey = "itemId" // For passing the item ID via the nav graph
    val routeWithArgs = "$route/{$itemIdKey}"
}