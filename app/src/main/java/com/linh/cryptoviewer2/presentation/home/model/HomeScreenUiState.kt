package com.linh.cryptoviewer2.presentation.home.model

sealed class HomeScreenUiState(val searchQuery: String? = null, val onQueryChange: (String) -> Unit) {

    class Initial(onQueryTextChange: (String) -> Unit) : HomeScreenUiState(null, onQueryTextChange)

    data class Loading(
        val query: String? = null,
        val results: List<SearchResultUi>,
        val onQueryTextChange: (String) -> Unit
    ): HomeScreenUiState(query, onQueryTextChange)

    data class Result(
        val query: String? = null,
        val results: List<SearchResultUi>,
        val onQueryTextChange: (String) -> Unit
    ) : HomeScreenUiState(query, onQueryTextChange)

    data class Error(
        val query: String? = null,
        val errorMessage: String,
        val onQueryTextChange: (String) -> Unit
    ) : HomeScreenUiState(query, onQueryTextChange)
}