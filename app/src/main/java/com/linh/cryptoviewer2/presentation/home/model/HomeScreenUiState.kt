package com.linh.cryptoviewer2.presentation.home.model

sealed class HomeScreenUiState(val searchQuery: String? = null, val onQueryChange: (query: String, shouldSearchImmediately: Boolean) -> Unit) {

    class Initial(onQueryTextChange: (query: String, shouldSearchImmediately: Boolean) -> Unit) : HomeScreenUiState(null, onQueryTextChange)

    data class Loading(
        val query: String? = null,
        val results: List<SearchResultUi>,
        val onQueryTextChange: (query: String, shouldSearchImmediately: Boolean) -> Unit
    ): HomeScreenUiState(query, onQueryTextChange)

    data class Result(
        val query: String? = null,
        val results: List<SearchResultUi>,
        val onQueryTextChange: (query: String, shouldSearchImmediately: Boolean) -> Unit
    ) : HomeScreenUiState(query, onQueryTextChange)

    data class Error(
        val query: String? = null,
        val errorMessage: String,
        val onQueryTextChange: (query: String, shouldSearchImmediately: Boolean) -> Unit
    ) : HomeScreenUiState(query, onQueryTextChange)
}