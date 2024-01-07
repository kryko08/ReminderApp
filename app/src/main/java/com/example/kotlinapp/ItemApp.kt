package com.example.kotlinapp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kotlinapp.ui.home.HomeUiState
import com.example.kotlinapp.ui.navigation.ItemNavHost
import kotlinx.coroutines.delay


// This is the main entry point of our app, returns the router
@Composable
fun ItemApp(navController: NavHostController = rememberNavController()){
    ItemNavHost(navController = navController)
}


// Top App bar shown on every screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior? = null,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.back_button))
                }
            }
        }
    )
}

private const val SplashWaitTime: Long = 2000

@Composable
fun landingScreen(
    onTimeout: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment=Alignment.Center){
        val currentOnTimeout by rememberUpdatedState(onTimeout)
        
        LaunchedEffect(Unit){
            delay(SplashWaitTime)
            currentOnTimeout()
        }
        
        Image(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null)

    }
}
