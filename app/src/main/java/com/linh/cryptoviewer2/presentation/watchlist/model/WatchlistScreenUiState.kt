package com.linh.cryptoviewer2.presentation.watchlist.model

sealed class WatchlistScreenUiState {
    object Initial: WatchlistScreenUiState()
    data class Loading(val oldData: List<CoinUi>? = null): WatchlistScreenUiState()
    data class Success(val data: HomeScreenSuccessUi): WatchlistScreenUiState()
    data class Error(val errorMessage: String): WatchlistScreenUiState()

    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
}

class HomeScreenSuccessUi(
    val data: List<CoinUi>,
    val onRefresh: () -> Unit
)