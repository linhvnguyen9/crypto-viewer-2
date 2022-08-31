package com.linh.cryptoviewer2.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.linh.cryptoviewer2.presentation.components.CoinGeckoAttribution
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenUiState
import com.linh.cryptoviewer2.presentation.home.model.SearchResultUi

@Composable
fun HomeScreen(uiState: HomeScreenUiState) {
    Column(Modifier.padding(16.dp)) {
        SearchHeader(uiState.searchQuery, uiState.onQueryChange)

        Spacer(Modifier.height(16.dp))

        when (uiState) {
            is HomeScreenUiState.Result -> SuccessState(uiState)
            is HomeScreenUiState.Error -> ErrorState(uiState)
            is HomeScreenUiState.Loading -> LoadingState()
        }
    }
}

@Composable
private fun SuccessState(uiState: HomeScreenUiState.Result) {
    LazyColumn(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(uiState.results) { _: Int, item: SearchResultUi ->
            SearchResultItem(searchResultUi = item)
        }

        item {
            CoinGeckoAttribution()
        }
    }
}

@Composable
private fun ErrorState(uiState: HomeScreenUiState.Error) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(uiState.errorMessage)
    }
}

@Composable
private fun LoadingState() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
private fun SearchHeader(query: String?, onQueryChange: (query: String, shouldSearchImmediately: Boolean) -> Unit) {
    TextField(
        value = query.orEmpty(),
        onValueChange = { onQueryChange(it, false) },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 1,
        leadingIcon = {
            Icon(Icons.Filled.Search, null)
        },
        trailingIcon = {
            if (!query.isNullOrBlank()) {
                IconButton(
                    onClick = { onQueryChange("", false) },
                    content = {
                        Icon(Icons.Filled.Clear, null)
                    }
                )
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onQueryChange(query.orEmpty(), true)} )
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(uiState = HomeScreenUiState.Initial(onQueryTextChange = { query: String, shouldSearchImmediately: Boolean ->  }))
}