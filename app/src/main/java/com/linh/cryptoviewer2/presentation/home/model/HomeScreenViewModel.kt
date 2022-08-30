package com.linh.cryptoviewer2.presentation.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.cryptoviewer2.R
import com.linh.cryptoviewer2.domain.usecase.AddCoinToWatchlistUseCase
import com.linh.cryptoviewer2.domain.usecase.RemoveCoinFromWatchlistUseCase
import com.linh.cryptoviewer2.domain.usecase.SearchUseCase
import com.linh.cryptoviewer2.presentation.util.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val searchUseCase: SearchUseCase,
    private val searchResultToSearchResultUiMapper: SearchResultToSearchResultUiMapper,
    private val addCoinToWatchlistUseCase: AddCoinToWatchlistUseCase,
    private val removeCoinFromWatchlistUseCase: RemoveCoinFromWatchlistUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Initial(this::onQueryChange))
    val uiState: StateFlow<HomeScreenUiState> get() = _uiState

    private var searchJob: Job? = null

    private fun onQueryChange(query: String, shouldSearchImmediately: Boolean) {
        _uiState.update {
            val oldList = (it as? HomeScreenUiState.Result)?.results
            HomeScreenUiState.Result(query, oldList.orEmpty(), this::onQueryChange)
        }

        searchJob?.cancel()

        if (query.isBlank()) return

        searchJob = viewModelScope.launch {
            if (shouldSearchImmediately.not()) {
                delay(DEBOUNCE_TIME_MILLIS)
            }

            _uiState.update {
                val oldList = (it as? HomeScreenUiState.Result)?.results
                HomeScreenUiState.Loading(it.searchQuery, oldList.orEmpty(), it.onQueryChange)
            }

            try {
                val result = searchUseCase(query)
                val uiModel = searchResultToSearchResultUiMapper.map(result, this@HomeScreenViewModel::onToggleWatch)

                _uiState.update { HomeScreenUiState.Result(query, uiModel, this@HomeScreenViewModel::onQueryChange) }
            } catch (e: Exception) {
                _uiState.update { HomeScreenUiState.Error(query, resourceProvider.getString(R.string.home_error_occurred), this@HomeScreenViewModel::onQueryChange) }
            }
        }
    }

    private fun onToggleWatch(isWatch: Boolean, coinId: String) {
        viewModelScope.launch {
            if (isWatch) {
                addCoinToWatchlistUseCase(coinId)
            } else {
                removeCoinFromWatchlistUseCase(coinId)
            }
        }
    }

    companion object {
        const val DEBOUNCE_TIME_MILLIS = 1000L
    }
}