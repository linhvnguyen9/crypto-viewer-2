package com.linh.cryptoviewer2.presentation.home.model

sealed class HomeScreenUiState {
    object Initial: HomeScreenUiState()
    data class Loading(val oldData: List<CoinUi>? = null): HomeScreenUiState()
    data class Success(val data: HomeScreenSuccessUi): HomeScreenUiState()
    data class Error(val errorMessage: String): HomeScreenUiState()

    val isLoading: Boolean get() = this is Loading
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
}

class HomeScreenSuccessUi(
    val data: List<CoinUi>,
    val onRefresh: () -> Unit
)