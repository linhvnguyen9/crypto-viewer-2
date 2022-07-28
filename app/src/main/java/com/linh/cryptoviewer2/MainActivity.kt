package com.linh.cryptoviewer2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.linh.cryptoviewer2.presentation.home.HomeScreen
import com.linh.cryptoviewer2.presentation.home.HomeViewModel
import com.linh.cryptoviewer2.ui.theme.CryptoViewer2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            CryptoViewer2Theme {
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