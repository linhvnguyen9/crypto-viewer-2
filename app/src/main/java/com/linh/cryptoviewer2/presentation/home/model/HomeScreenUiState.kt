package com.linh.cryptoviewer2.presentation.home.model

import com.linh.cryptoviewer2.presentation.watchlist.model.CoinUi

sealed class HomeScreenUiState(val searchQuery: String? = null, val onQueryChange: (String) -> Unit) {
    class Initial(onQueryTextChange: (String) -> Unit): HomeScreenUiState(null, onQueryTextChange)
    data class Result(
        val query: String? = null,
        val results: List<CoinUi>,
        val onQueryTextChange: (String) -> Unit
    ): HomeScreenUiState(query, onQueryTextChange)
}