package com.linh.cryptoviewer2.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linh.cryptoviewer2.R
import com.linh.cryptoviewer2.domain.model.ConnectivityState
import com.linh.cryptoviewer2.presentation.watchlist.WatchlistScreen
import com.linh.cryptoviewer2.presentation.watchlist.WatchlistViewModel
import com.linh.cryptoviewer2.presentation.theme.CryptoViewer2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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
                        scaffoldState.snackbarHostState.showSnackbar(getString(R.string.snackbar_no_internet), duration = SnackbarDuration.Indefinite)
                    } else {
                        scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    }
                }

                Scaffold(
                    scaffoldState = scaffoldState,
                    bottomBar = {
                        BottomNavigation {
                            BottomNavigationItem(
                                selected = true,
                                onClick = { /*TODO*/ },
                                icon = { Icon(Icons.Filled.Home, contentDescription = null) })
                            BottomNavigationItem(
                                selected = true,
                                onClick = { /*TODO*/ },
                                icon = { Icon(Icons.Filled.Star, contentDescription = null) })
                        }
                    }
                ) {
                    NavHost(navController = navController, startDestination = "/") {
                        composable("/") {
                            val viewModel: WatchlistViewModel = hiltViewModel()
                            val state = viewModel.uiState.collectAsState()

                            LaunchedEffect(key1 = true) {
                                viewModel.getCoin("binancecoin")
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