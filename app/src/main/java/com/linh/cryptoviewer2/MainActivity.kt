package com.linh.cryptoviewer2

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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linh.cryptoviewer2.domain.model.ConnectivityState
import com.linh.cryptoviewer2.presentation.home.HomeScreen
import com.linh.cryptoviewer2.presentation.home.HomeViewModel
import com.linh.cryptoviewer2.ui.theme.CryptoViewer2Theme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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
                            val viewModel: HomeViewModel = hiltViewModel()
                            val state = viewModel.uiState.collectAsState()

                            LaunchedEffect(key1 = true) {
                                viewModel.getCoin("binancecoin")
                            }

                            Surface(
                                modifier = Modifier.fillMaxSize(),
                                color = MaterialTheme.colors.background
                            ) {
                                HomeScreen(state.value)
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