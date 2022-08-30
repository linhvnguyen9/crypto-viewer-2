package com.linh.cryptoviewer2.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.linh.cryptoviewer2.R
import com.linh.cryptoviewer2.domain.model.ConnectivityState
import com.linh.cryptoviewer2.presentation.home.HomeScreen
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenViewModel
import com.linh.cryptoviewer2.presentation.navigation.NavigationDestination
import com.linh.cryptoviewer2.presentation.navigation.Navigator
import com.linh.cryptoviewer2.presentation.watchlist.WatchlistScreen
import com.linh.cryptoviewer2.presentation.watchlist.WatchlistViewModel
import com.linh.cryptoviewer2.presentation.theme.CryptoViewer2Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CryptoViewer2Theme {
                val connectivityState = viewModel.connectivityState.collectAsState()

                val scaffoldState = rememberScaffoldState()

                LaunchedEffect(connectivityState.value) {
                    if (connectivityState.value == ConnectivityState.DISCONNECTED) {
                        scaffoldState.snackbarHostState.showSnackbar(
                            getString(R.string.snackbar_no_internet),
                            duration = SnackbarDuration.Indefinite
                        )
                    } else {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val selectedDestination =
                    navBackStackEntry?.destination?.route ?: navigator.startDestination.route

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = selectedDestination == NavigationDestination.Home.route,
                                onClick = { viewModel.navigateToHome() },
                                icon = { Icon(Icons.Filled.Home, contentDescription = null) },
                                label = { Text(getString(R.string.all_home)) }
                            )
                            BottomNavigationItem(
                                selected = selectedDestination == NavigationDestination.Watchlist.route,
                                onClick = { viewModel.navigateToWatchlist() },
                                icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                                label = { Text(getString(R.string.all_watchlist)) }
                            )
                        }
                    }
                ) { innerPadding ->
                    LaunchedEffect(Unit) {
                        viewModel.navigationCommands.collect { command ->
                            command.let {
                                if (it.destination.route.isNotEmpty()) {
                                    navController.navigate(
                                        it.destination.route
                                    )
                                }
                            }
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = navigator.startDestination.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(NavigationDestination.Home.route) {
                            val viewModel: HomeScreenViewModel = hiltViewModel()
                            val state = viewModel.uiState.collectAsState()

                            HomeScreen(uiState = state.value)
                        }
                        composable(NavigationDestination.Watchlist.route) {
                            val viewModel: WatchlistViewModel = hiltViewModel()
                            val state = viewModel.uiState.collectAsState()

                            LaunchedEffect(key1 = true) {
                                viewModel.getCoin()
                            }

                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {
                                WatchlistScreen(state.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CryptoViewer2Theme {
        Greeting("Android")
    }
}