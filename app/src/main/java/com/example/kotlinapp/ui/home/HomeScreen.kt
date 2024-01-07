package com.example.kotlinapp.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialogDefaults.shape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinapp.R
import com.example.kotlinapp.TopAppBar
import com.example.kotlinapp.data.Item
import com.example.kotlinapp.ui.AppViewModelProvider
import com.example.kotlinapp.ui.item.ItemDetails
import com.example.kotlinapp.ui.navigation.NavigationDestination
import java.nio.file.WatchEvent

object HomeDestination: NavigationDestination {
    override val route = "home"
    override val title = R.string.home_title
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToItemEntry: () -> Unit,
    navigateToItemUpdate: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory=AppViewModelProvider.Factory)
) {
    // whenever there is a change in the uiState value, recomposition occurs for the composables using the gameUiState value.
    val homeUiState by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = stringResource(HomeDestination.title),
                scrollBehavior = scrollBehavior,
                canNavigateBack = false
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.action_button_padding))
                ){
                Icon(imageVector = Icons.Filled.Add, contentDescription = stringResource(R.string.action_button_add_text))
            }

        },
    ){ innerPadding ->
        HomeScreenBody(
            itemList = homeUiState.itemList,
            sortBy = homeUiState.sortType,
            onItemClick = navigateToItemUpdate,
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
            )

    }
}


@Composable
fun HomeScreenBody(
    itemList: List<ItemDetails>,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
    sortBy: SortBy
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(15.dp)
    ) {
        if (itemList.isEmpty()){
            Text(
                text = stringResource(R.string.home_no_items),
                fontSize = 15.sp
            )
        } else {
            SortTypeDropDownMenu(sortBy = sortBy, onValueChange = {})
            HomeScreenItemList(
                items = itemList,
                onItemClick = { onItemClick(it.id) }
            )
        }
    }
}

@Composable
fun HomeScreenItemList(
    items: List<ItemDetails>,
    modifier: Modifier = Modifier,
    onItemClick: (ItemDetails) -> Unit
){
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ){
        items(items = items, key = { it.id }){ item ->
            HomeScreenItem(
                item = item,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClick(item) }
            )
        }
    }
}

@Composable
fun HomeScreenItem(
    item: ItemDetails,
    modifier: Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ){
        Column(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = item.header.take(11) + "...",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "Priority: ",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = item.priority.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge,
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = item.description.take(10) + "...",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortTypeDropDownMenu(
    sortBy: SortBy,
    onValueChange: (ItemDetails) -> Unit,
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { newValue ->
                isExpanded = newValue
            },
        )
        {
            TextField(
                value = sortBy.toString(),
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                placeholder = {
                    Text(text = "Select a note priority")
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                }
            ) {
                DropdownMenuItem(
                    text = {
                        Text(text = "Low")
                    },
                    onClick = {

                        isExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(text = "Medium")
                    },
                    onClick = {

                        isExpanded = false
                    }
                )
            }
        }
    }
}