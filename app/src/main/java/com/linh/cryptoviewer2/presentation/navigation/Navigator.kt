package com.linh.cryptoviewer2.presentation.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface Navigator {
    val commands: StateFlow<NavigationCommand>

    val startDestination: NavigationDestination

    fun navigate(command: NavigationCommand)

    fun navigateToHome()

    fun navigateToWatchlist()
}

data class NavigationCommand(val destination: NavigationDestination, val popUpTo: NavigationDestination? = null, val inclusive: Boolean = false)