package com.linh.cryptoviewer2.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.linh.cryptoviewer2.presentation.home.model.CoinUi
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenUiState

@Composable
fun HomeScreen(uiState: HomeScreenUiState) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState) {
            HomeScreenUiState.Initial, HomeScreenUiState.Loading -> HomeScreenLoadingState()
            is HomeScreenUiState.Error -> HomeScreenErrorState()
            is HomeScreenUiState.Success -> HomeScreenSuccessState(uiState.data)
        }
    }
}

@Composable
fun HomeScreenErrorState() {
    Text(text = "An error occurred")
}

@Composable
fun HomeScreenLoadingState() {
    CircularProgressIndicator()
}

@Composable
fun HomeScreenSuccessState(coinUi: CoinUi) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        repeat(20) {
            item { CoinItem(coinUi) }
        }
    }
}

@Composable
fun CoinItem(coinUi: CoinUi) {
    with(coinUi) {
        Card {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(Modifier.width(16.dp))
                Column(Modifier.wrapContentHeight()) {
                    Text(name)
                    Text(symbol)
                }
            }
        }
    }
}

@Preview
@Composable
fun CoinItemPreview() {
    CoinItem(coinUi = CoinUi("Test", "Symbol", "Url"))
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(uiState = HomeScreenUiState.Error(""))
}