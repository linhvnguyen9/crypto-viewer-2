package com.linh.cryptoviewer2.presentation.home.model

sealed class HomeScreenUiState {
    object Initial: HomeScreenUiState()
    object Loading: HomeScreenUiState()
    data class Success(val data: CoinUi): HomeScreenUiState()
    data class Error(val errorMessage: String): HomeScreenUiState()
}