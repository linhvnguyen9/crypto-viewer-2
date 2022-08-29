package com.linh.cryptoviewer2.presentation.home.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linh.cryptoviewer2.domain.usecase.SearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val searchUseCase: SearchUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Initial(this::onQueryChange))
    val uiState: StateFlow<HomeScreenUiState> get() = _uiState

    private var searchJob: Job? = null

    private fun onQueryChange(query: String) {
        _uiState.update { HomeScreenUiState.Result(query, emptyList(), this::onQueryChange) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(DEBOUNCE_TIME_MILLIS)
            searchUseCase(query)
        }
    }

    companion object {
        const val DEBOUNCE_TIME_MILLIS = 1000L
    }
}