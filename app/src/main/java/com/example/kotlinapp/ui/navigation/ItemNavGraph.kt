package com.example.kotlinapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavArgument
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.kotlinapp.ui.home.HomeDestination
import com.example.kotlinapp.ui.home.HomeScreen
import com.example.kotlinapp.ui.item.ItemDetailScreen
import com.example.kotlinapp.ui.item.ItemDetailsDestination
import com.example.kotlinapp.ui.item.ItemEntryDestination
import com.example.kotlinapp.ui.item.ItemEntryScreen

@Composable
fun ItemNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(HomeDestination.route) {
            HomeScreen(
                navigateToItemEntry = { navController.navigate(ItemEntryDestination.route) },
                navigateToItemUpdate = { navController.navigate("${ItemDetailsDestination.route}/${it}")}
            )
        }
        composable(ItemEntryDestination.route) {
            ItemEntryScreen(
                navigateBack = {navController.popBackStack()},
                navigateUp = {navController.navigateUp()}
            )
        }
        composable(
            ItemDetailsDestination.routeWithArgs,
            listOf(navArgument(ItemDetailsDestination.itemIdKey) {
            type = NavType.IntType
        })
        ){
            ItemDetailScreen(
                navigateBack = {navController.popBackStack()},
                navigateUp = {navController.navigateUp()})
        }
    }
}