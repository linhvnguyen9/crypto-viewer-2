package com.linh.cryptoviewer2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.cryptoviewer2.domain.usecase.GetCoinUseCase
import com.linh.cryptoviewer2.presentation.home.model.CoinToCoinUiMapper
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinUseCase: GetCoinUseCase,
    private val coinUiMapper: CoinToCoinUiMapper
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Initial)
    val uiState: StateFlow<HomeScreenUiState> get() = _uiState

    fun getCoin(coinId: String) {
        _uiState.update { HomeScreenUiState.Loading }

        viewModelScope.launch {
            try {
                val response = getCoinUseCase(coinId)
                val uiModel = coinUiMapper.map(response)
                _uiState.update { HomeScreenUiState.Success(uiModel) }
            } catch (e: Exception) {
                _uiState.update { HomeScreenUiState.Error("Test error") }
            }
        }
    }
}