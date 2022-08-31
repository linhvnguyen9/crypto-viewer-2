package com.linh.cryptoviewer2.presentation.watchlist

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
import com.linh.cryptoviewer2.presentation.components.CoinGeckoAttribution
import com.linh.cryptoviewer2.presentation.components.CoinItem
import com.linh.cryptoviewer2.presentation.watchlist.model.CoinUi
import com.linh.cryptoviewer2.presentation.watchlist.model.WatchlistScreenUiState

@Composable
fun WatchlistScreen(uiState: WatchlistScreenUiState) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        when (uiState) {
            is WatchlistScreenUiState.Error -> WatchlistScreenErrorState(uiState.errorMessage)
            else -> {
                SwipeRefresh(
                    state = rememberSwipeRefreshState(isRefreshing = uiState.isLoading),
                    onRefresh = { if (uiState is WatchlistScreenUiState.Success) uiState.data.onRefresh() }
                ) {
                    if (uiState.isSuccess || uiState.isLoading) {
                        val data = when (uiState) {
                            is WatchlistScreenUiState.Success -> uiState.data.data
                            is WatchlistScreenUiState.Loading -> uiState.oldData ?: emptyList()
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
fun WatchlistScreenErrorState(errorMessage: String) {
    Text(text = errorMessage)
}

@Preview
@Composable
fun WatchlistScreenPreview() {
    WatchlistScreen(uiState = WatchlistScreenUiState.Error(""))
}