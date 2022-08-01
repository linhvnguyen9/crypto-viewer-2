package com.linh.cryptoviewer2.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.linh.cryptoviewer2.presentation.home.model.CoinUi
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenUiState

@Composable
fun HomeScreen(uiState: HomeScreenUiState) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState) {
            is HomeScreenUiState.Error -> HomeScreenErrorState(uiState.errorMessage)
            else -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = uiState.isLoading),
                    onRefresh = { if (uiState is HomeScreenUiState.Success) uiState.data.onRefresh() }
                ) {
                    if (uiState.isSuccess || uiState.isLoading) {
                        val data = when (uiState) {
                            is HomeScreenUiState.Success -> uiState.data.data
                            is HomeScreenUiState.Loading -> uiState.oldData ?: emptyList()
                            else -> emptyList()
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            itemsIndexed(data) { _, item ->
                                CoinItem(coinUi = item)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenErrorState(errorMessage: String) {
    Text(text = errorMessage)
}

@Composable
fun HomeScreenLoadingState() {
    CircularProgressIndicator()
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
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.width(16.dp))
                Box(Modifier.fillMaxWidth()) {
                    Column(Modifier.wrapContentHeight()) {
                        Text(name, style = MaterialTheme.typography.h6)
                        Spacer(Modifier.height(4.dp))
                        Text(symbol, style = MaterialTheme.typography.subtitle2)
                    }

                    Column(
                        Modifier
                            .wrapContentHeight()
                            .align(Alignment.CenterEnd)) {
                        Text(displayPrice, style = MaterialTheme.typography.h6)
                        Spacer(Modifier.height(4.dp))
                        Row(Modifier.align(Alignment.End), verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                painter = painterResource(id = priceChangeIconRes),
                                contentDescription = null,
                                tint = colorResource(id = priceChangeDataColorRes)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                priceChangePercentage24hText,
                                style = MaterialTheme.typography.subtitle2,
                                color = colorResource(id = priceChangeDataColorRes)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CoinItemPreview() {
    CoinItem(coinUi = CoinUi("Test", "Symbol", "Url", 1000.2, 1.23))
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(uiState = HomeScreenUiState.Error(""))
}