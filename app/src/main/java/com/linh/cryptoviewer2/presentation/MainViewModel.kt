package com.linh.cryptoviewer2.presentation

import android.view.View
import androidx.lifecycle.ViewModel
import com.linh.cryptoviewer2.domain.model.ConnectivityState
import com.linh.cryptoviewer2.domain.usecase.GetConnectivityStateUseCase
import com.linh.cryptoviewer2.presentation.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getConnectivityStateUseCase: GetConnectivityStateUseCase,
    private val navigator: Navigator
): ViewModel() {
    val connectivityState = getConnectivityStateUseCase()
    val navigationCommands = navigator.commands

    fun navigateToHome() {
        navigator.navigateToHome()
    }

    fun navigateToWatchlist() {
        navigator.navigateToWatchlist()
    }
}