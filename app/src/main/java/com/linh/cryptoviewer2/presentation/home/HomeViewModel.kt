package com.linh.cryptoviewer2.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.cryptoviewer2.R
import com.linh.cryptoviewer2.domain.model.FiatCurrency
import com.linh.cryptoviewer2.domain.model.PriceChangePercentage
import com.linh.cryptoviewer2.domain.usecase.GetCoinMarketDataUseCase
import com.linh.cryptoviewer2.domain.usecase.GetCoinUseCase
import com.linh.cryptoviewer2.presentation.home.model.CoinMarketDataToCoinUiMapper
import com.linh.cryptoviewer2.presentation.home.model.CoinToCoinUiMapper
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenSuccessUi
import com.linh.cryptoviewer2.presentation.home.model.HomeScreenUiState
import com.linh.cryptoviewer2.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinMarketDataUseCase: GetCoinMarketDataUseCase,
    private val coinMarketDataToCoinUiMapper: CoinMarketDataToCoinUiMapper,
    private val resourceProvider: ResourceProvider
): ViewModel() {

    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Initial)
    val uiState: StateFlow<HomeScreenUiState> get() = _uiState

    private var coinId = ""

    fun getCoin(coinId: String) {
        this.coinId = coinId // TODO: Load this data from DB instead of passing in arg

        _uiState.update {
            val oldList = (it as? HomeScreenUiState.Success)?.data?.data
            HomeScreenUiState.Loading(oldList)
        }

        viewModelScope.launch {
            try {
                val response = getCoinMarketDataUseCase(FiatCurrency.USD, listOf(coinId), listOf(PriceChangePercentage.CHANGE_24_HOURS))
                val uiModel = coinMarketDataToCoinUiMapper.map(response)
                _uiState.update { HomeScreenUiState.Success(HomeScreenSuccessUi(uiModel, onRefresh = this@HomeViewModel::refresh)) }
            } catch (e: Exception) {
                Timber.e(e)
                _uiState.update { HomeScreenUiState.Error(resourceProvider.getString(R.string.home_error_occurred)) }
            }
        }
    }

    private fun refresh() {
        getCoin(coinId)
    }
}