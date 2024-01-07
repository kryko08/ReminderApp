package com.example.kotlinapp.ui.item

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinapp.R
import com.example.kotlinapp.TopAppBar
import com.example.kotlinapp.data.Item
import com.example.kotlinapp.ui.AppViewModelProvider
import com.example.kotlinapp.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object ItemDetailsDestination: NavigationDestination {
    override val route = "item_details"
    override val title = R.string.item_details_title
    const val itemIdKey = "itemId" // For passing the item ID via the nav graph
    val routeWithArgs = "$route/{$itemIdKey}"
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ItemDetailScreen(
    navigateBack: () -> Unit,
    navigateUp: () -> Unit,
    canNavigateBack: Boolean = true,
    viewModel: ItemDetailViewModel = viewModel(factory = AppViewModelProvider.Factory)
){
    val coroutineScope = rememberCoroutineScope() // composition-aware scope to launch a coroutine outside a composable, canceled when composable leaves composition
    val uiState by viewModel.uiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = stringResource(id = R.string.item_details_title),
                navigateUp = navigateUp,
                canNavigateBack = canNavigateBack,
            )
        }
    ){ innerPadding ->
        ItemDetailBody(
            itemUiState = uiState,
            onDeleteClick = {
                coroutineScope.launch {
                    viewModel.deleteItem()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )

    }

}

@Composable
fun ItemDetailBody(
    itemUiState: ItemDetailUiState,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_large))
    ) {
        ItemDetail(
            itemDetails = itemUiState.itemDetails,
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Button(
                onClick = onDeleteClick,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.delete_action))
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetail(
    itemDetails: ItemDetails,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
    ) {
        Column {
            Text(text = "Header:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = itemDetails.header, fontSize = 18.sp)
        }
        Column {
            Text(text = "Description:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = itemDetails.description, fontSize = 18.sp)
        }
        Column {
            Text(text = "Priority:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = itemDetails.priority.lowercase().replaceFirstChar { it.uppercase() }, fontSize = 18.sp)
        }
    }
}



