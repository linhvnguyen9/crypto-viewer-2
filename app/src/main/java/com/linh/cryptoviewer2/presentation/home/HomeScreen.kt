package com.linh.cryptoviewer2.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.linh.cryptoviewer2.presentation.components.CoinItem
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenUiState
import com.linh.cryptoviewer2.presentation.watchlist.model.CoinUi

@Composable
fun HomeScreen(uiState: HomeScreenUiState) {
    LazyColumn(
        Modifier
            .fillMaxSize()
    ) {
        item {
            SearchHeader(uiState.searchQuery, uiState.onQueryChange)
        }

        if (uiState is HomeScreenUiState.Result) {
            itemsIndexed(uiState.results) { _: Int, item: CoinUi ->
                CoinItem(coinUi = item)
            }
        }
    }
}

@Composable
private fun SearchHeader(query: String?, onQueryChange: (String) -> Unit) {
    TextField(
        value = query.orEmpty(),
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        leadingIcon = {
            Icon(Icons.Filled.Search, null)
        },
        trailingIcon = {
            if (!query.isNullOrBlank()) {
                IconButton(
                    onClick = { onQueryChange("") },
                    content = {
                        Icon(Icons.Filled.Clear, null)
                    }
                )
            }
        }
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(uiState = HomeScreenUiState.Initial {})
}